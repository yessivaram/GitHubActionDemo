package resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pojo.request.AddNewPetRequest;
import pojo.request.Category;
import pojo.request.Tag;
import pojo.response.AddNewPetResponse;
import pojo.response.CategoryResponse;
import pojo.response.TagResponse;
import pojo.response.UploadImageResponse;

public class TestDataBuild {

	public AddNewPetRequest AddNewPetRequestPayLoad(Integer petId, Integer catId, String catName, String name, String url, Integer tagId, String tagName, String status)
	{
		List<String> myList = new ArrayList<>();
		Category cat = new Category();
		cat.setId(catId);
		cat.setName(catName);
		
		Tag tag = new Tag();
		tag.setId(tagId);
		tag.setName(tagName);	
		
		AddNewPetRequest petRequest = new AddNewPetRequest();
		petRequest.setId(petId);
		petRequest.setCategory(cat);
		petRequest.setName(name);
		myList.add(url);
		petRequest.setPhotoUrls(myList);
		petRequest.setTags(Arrays.asList(tag));
		petRequest.setStatus(status);
		
		return petRequest;
	}
	
	public AddNewPetResponse AddNewPetResponsePayLoad(Integer petId, Integer catId, String catName, String name, String url, Integer tagId, String tagName, String status)
	{
		CategoryResponse catRes = new CategoryResponse();
		catRes.setId(catId);
		catRes.setName(catName);
		
		TagResponse tagRes = new TagResponse();
		tagRes.setId(tagId);
		tagRes.setName(tagName);
		
		AddNewPetResponse petResponse = new AddNewPetResponse();
		petResponse.setId(petId);
		petResponse.setCategory(catRes);
		petResponse.setName(name);
		petResponse.setPhotoUrls(Arrays.asList(url));
		petResponse.setTags(Arrays.asList(tagRes));
		petResponse.setStatus(status);
		
		return petResponse;
	}
	
	public UploadImageResponse UploadImageResponsePayload(Integer code, String type, String message)
	{		
		UploadImageResponse uploadResponse = new UploadImageResponse();
		uploadResponse.setCode(code);
		uploadResponse.setType(type);
		uploadResponse.setMessage(message);
		
		return uploadResponse;
	}

}
