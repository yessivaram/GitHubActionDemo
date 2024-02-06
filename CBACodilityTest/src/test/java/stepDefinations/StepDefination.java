package stepDefinations;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.request.AddNewPetRequest;
import pojo.request.Category;
import pojo.request.Tag;
import pojo.response.AddNewPetResponse;
import pojo.response.UploadImageResponse;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils {
	
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data =new TestDataBuild();	
	static String place_id;
	
	AddNewPetRequest petRequest;	
	Category cat;
	Tag tag;
	APIResources api;
	UploadImageResponse uploadResponse;
	AddNewPetResponse addNewPetResponse;
	
	@Given("Add a new pet to the store payload available")
	public void add_a_new_pet_to_the_store_payload_available() { 	}

	@When("^User calls add a new pet to the store API (.+) with (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void user_calls_add_a_new_pet_to_the_sotre_API_with_Black_Black_Doggie_D_CBA_Interview_Swagger_dog_jpg_Tall_Doggie_available(String resource, Integer petId, Integer catId, String catName, String name, String url, Integer tagId, String tagName, String status) throws IOException {
		
		APIResources resourceAPI = APIResources.valueOf(resource.replaceAll("\"", ""));		
				
		response = given().spec(requestSpecification()).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(data.AddNewPetRequestPayLoad(petId, catId, catName, name, url, tagId, tagName, status))
				.when().post(resourceAPI.getResource());
	}
		
	@Then("API call got success with status code {int}")
	public void api_call_got_success_with_status_code(Integer expStatus) {		
		org.junit.Assert.assertEquals(response.getStatusCode(), 200);		
	}

	@Then("^Validate all values and schema in the response body are as expected (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void validate_all_values_in_the_response_body_are_as_expected(Integer petId, Integer catId, String catName, String name, String url, Integer tagId, String tagName, String status) {
		
		AddNewPetResponse res = response.as(AddNewPetResponse.class);
		
		Assert.assertEquals(res.getId(), petId);
		Assert.assertEquals(res.getCategory().getId(), catId);
		Assert.assertEquals(res.getCategory().getName(), catName);
		Assert.assertEquals(res.getName(), name);
		Assert.assertEquals(res.getTags().get(0).getId(), tagId);
		Assert.assertEquals(res.getTags().get(0).getName(), tagName);
		Assert.assertEquals(res.getStatus(), status);		
	}
			
	@Given("Pet already added to the store")
	public void pet_already_added_to_the_store() {	}

	@When("^User calls Find pet by ID API (.+) with (.+)$")
	public void user_calls_Find_pet_by_ID_API_with(String resource, String petId) throws IOException {
				
		APIResources resourceAPI = APIResources.valueOf(resource.replaceAll("\"", ""));
		response = 	given().spec(requestSpecification()).pathParam("petid", petId)
					.when().get(resourceAPI.getResource());		

	}
	
	@Then("^API call got success with status code (.+) and error message (.+)$")
	public void api_call_got_success_with_status_code_and_error_message_Pet_not_found(Integer code, String message) {
		String actualStatusCode = code.toString();
		Assert.assertTrue(actualStatusCode.contains(code.toString()));
		Assert.assertTrue(getJsonPath(response, "message").contains(message));
	    
	}

	@When("^User calls Find pet by Status API (.+) with (.+)$")
	public void user_calls_Find_pet_by_Status_API_with_pending(String resource, String status) throws IOException {
		
		APIResources resourceAPI = APIResources.valueOf(resource.replaceAll("\"", ""));
		response = 	given().spec(requestSpecification()).queryParam("status", status)
					.when().get(resourceAPI.getResource());	
	}
	
	@Then("^Validate the respose has only expected (.+) existing and not other status$")
	public void validate_the_respose_has_only_expected_available_existing_and_not_other_status(String status) {		
		
		if (status == "available") {
			Assert.assertEquals(getJsonPath(response,status), status);
			Assert.assertFalse(getJsonPath(response,status) == "pending");
			Assert.assertFalse(getJsonPath(response,status) == "sold");
		}
		if (status == "pending") {
			Assert.assertEquals(getJsonPath(response,status), status);
			Assert.assertFalse(getJsonPath(response,status) == "available");
			Assert.assertFalse(getJsonPath(response,status) == "sold");
		}
		if (status == "sold") {
			Assert.assertEquals(getJsonPath(response,status), status);
			Assert.assertFalse(getJsonPath(response,status) == "available");
			Assert.assertFalse(getJsonPath(response,status) == "pending");
		}
	}
	    
	
	@When("^User calls Uppload pet image API (.+) with (.+), (.+), (.+)$")
	public void user_calls_Uppload_pet_image_API_with_Pet_is_very_cute_D_CBA_Interview_Swagger_dog_jpg(String resource, Integer petId, String additionalMeta, String file) throws IOException {
		
		APIResources resourceAPI = APIResources.valueOf(resource.replaceAll("\"", ""));
		response = 	given().spec(requestSpecification()).header("accept","application/json").pathParam("petid", petId).formParam("additionalMetadata", additionalMeta)
					.multiPart("file", new File(file))	
					.when().post(resourceAPI.getResource());	
	}
	
	@Then("^Validate all values and schema in the response body for upload image are as expected (.+), (.+), (.+), (.+)$")
	public void validate_all_values_and_schema_in_the_response_body_are_as_expected_unknown_Pet_is_very_cute_dog_jpg(Integer code, String type, String Meta, String file) {

		UploadImageResponse uiRes = response.as(UploadImageResponse.class);		
		System.out.println(uiRes.getMessage());
		Assert.assertEquals(uiRes.getCode(), code);
		Assert.assertEquals(uiRes.getType(), type);
		Assert.assertTrue(uiRes.getMessage().contains(Meta));
		Assert.assertTrue(uiRes.getMessage().contains(file));
		
	}
	
	@When("^User calls update pet API (.+) with (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
	public void user_calls_update_pet_API_with_Black_White_Doggie_dog_jpg_Tall_Doggie_sold(String resource, Integer petId, Integer catId, String catName, String name, String url, Integer tagId, String tagName, String status) throws IOException {
	 
		APIResources resourceAPI = APIResources.valueOf(resource.replaceAll("\"", ""));		
		
		response = given().spec(requestSpecification()).contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(data.AddNewPetRequestPayLoad(petId, catId, catName, name, url, tagId, tagName, status))
				.when().put(resourceAPI.getResource());
		
	}
	
	@When("^User calls update pet with form data API (.+) with (.+), (.+), (.+)$")
	public void user_calls_update_pet_with_form_data_API_with_Yellow_Doggie_sold(String resource, String petId, String name, String status) throws IOException {
	    	
		APIResources resourceAPI = APIResources.valueOf(resource.replaceAll("\"", ""));		
		
		response = given().spec(requestSpecification()).header("Content-Type","application/x-www-form-urlencoded").accept(ContentType.JSON)		
				.formParam("name", name).formParam("status", status).pathParam("petid", petId)
				.when().post(resourceAPI.getResource());
	}
	
	@Then("^Validate all values and schema in the response body for update pet with form are as expected (.+),(.+),(.+)$")
	public void validate_all_values_and_schema_in_the_response_body_for_update_pet_with_form_are_as_expected_unknown(Integer code, String type, String message) {
		UploadImageResponse uiResp = response.as(UploadImageResponse.class);
		Assert.assertEquals(uiResp.getCode(), code);
		Assert.assertEquals(uiResp.getType().trim(), type.trim());
		Assert.assertEquals(uiResp.getMessage().trim(), message.trim());
	}

	@When("^User calls delete pet API (.+) with (.+)$")
	public void user_calls_delete_pet_API_with(String resource, String petId) throws IOException {
		
		APIResources resourceAPI = APIResources.valueOf(resource.replaceAll("\"", ""));		
		
		response = given().spec(requestSpecification()).accept(ContentType.JSON).pathParam("petid", petId)
				.when().delete(resourceAPI.getResource());

	}

	
	
	
	
	
	
	//	
//
//@Given("Add Place Payload with {string}  {string} {string}")
//public void add_Place_Payload_with(String name, String language, String address) throws IOException {
//	    // Write code here that turns the phrase above into concrete actions
//	
//		 
//		 res=given().spec(requestSpecification())
//		.body(data.addPlacePayLoad(name,language,address));
//	}
//
//@When("user calls {string} with {string} http request")
//public void user_calls_with_http_request(String resource, String method) {
//	    // Write code here that turns the phrase above into concrete actions
////constructor will be called with value of resource which you pass
//		APIResources resourceAPI=APIResources.valueOf(resource);
//		System.out.println(resourceAPI.getResource());
//		
//		
//		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
//		
//		if(method.equalsIgnoreCase("POST"))
//		 response =res.when().post(resourceAPI.getResource());
//		else if(method.equalsIgnoreCase("GET"))
//			 response =res.when().get(resourceAPI.getResource());
//		
//}
//
//	@Then("the API call got success with status code {int}")
//	public void the_API_call_got_success_with_status_code(Integer int1) {
//	    // Write code here that turns the phrase above into concrete actions
//		assertEquals(response.getStatusCode(),200);
//		
//	
//	}
//
//	@Then("{string} in response body is {string}")
//	public void in_response_body_is(String keyValue, String Expectedvalue) {
//	    // Write code here that turns the phrase above into concrete actions
//	  
//	 assertEquals(getJsonPath(response,keyValue),Expectedvalue);
//	}
//	
//	@Then("verify place_Id created maps to {string} using {string}")
//	public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
//	
//	   // requestSpec
//	     place_id=getJsonPath(response,"place_id");
//		 res=given().spec(requestSpecification()).queryParam("place_id",place_id);
//		 user_calls_with_http_request(resource,"GET");
//		  String actualName=getJsonPath(response,"name");
//		  assertEquals(actualName,expectedName);
//		 
//	    
//	}
//	
//
//@Given("DeletePlace Payload")
//public void deleteplace_Payload() throws IOException {
//    // Write code here that turns the phrase above into concrete actions
//   
//	res =given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
//}



}
