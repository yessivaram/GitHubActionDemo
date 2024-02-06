Feature: Everything about your Pets with postive and negative test cases


	Scenario Outline: 01_Add a new pet to the store with positive data 
		
	Given Add a new pet to the store payload available
	When User calls add a new pet to the store API "AddPetAPI" with <petId>, <catId>, <catName>, <name>, <url>, <tagId>, <tagname>, <status>
	Then API call got success with status code 200
	And Validate all values and schema in the response body are as expected <petId>, <catId>, <catName>, <name>, <url>, <tagId>, <tagname>, <status>

	Examples:
	|petId 	 | catId |catName		   |name        |url				|tagId|tagname	   |status		  |
	|1500  	 | 1     |Black        |Black Doggie|dog1.jpg		|10   |Tall Doggie |available   |
	|1501  	 | 2     |Brown        |Brown Doggie|dog2.jpg		|11   |Short Doggie|pending     |
   
  # Add a new pet to the store - Negative Test (NOT AUTOMATED DUE TO THE BELOW REASONS)
  # Defect - Expected - Invalid Input Code 405 as per the API contract document
  #          Actual - getting 500 server error, while passing Pet id as alphabhet "PET" instead of integer
  # Passing null values => No requirement provided, API generated its own id if null id provided.    
	
 
  Scenario Outline: 02_Find pet by valid ID 
		
	Given Pet already added to the store
	When User calls Find pet by ID API "GetPetByIdAPI" with <petId>
	Then API call got success with status code 200 		
	And Validate all values and schema in the response body are as expected <petId>, <catId>, <catName>, <name>, <url>, <tagId>, <tagname>, <status>
	
	Examples:
	|petId 	 | catId |catName		   |name        |url				|tagId|tagname	  |status		  |
	|1500  	 | 1     |Black        |Black Doggie|dog1.jpg		|10   |Tall Doggie|available  |


  Scenario Outline: 03_Find pet by invlid ID as Negative Test 
	
	Given Pet already added to the store
	When User calls Find pet by ID API "GetPetByIdAPI" with <petId>
	Then API call got success with status code 404 and error message <message>
	
	Examples:
	|petId 	   | message 													| 
	|5520300	 | Pet not found 										|
	|123 4   	 | java.lang.NumberFormatException	|
	|PET    	 | java.lang.NumberFormatException	|
	|null    	 | java.lang.NumberFormatException	|
	

  Scenario Outline: 04_Find pet by Status 
		
	Given Pet already added to the store
	When User calls Find pet by Status API "GetPetByStatusAPI" with <status>
	Then API call got success with status code 200
	And Validate the respose has only expected <status> existing and not other status

	Examples:
	|status  		 | 
	|available   |
	|pending     |
	|sold        |
 

  # Find pet by Status - Negative Test (NOT AUTOMATED DUE TO THE BELOW REASONS)
  # Defect - Expected - Invalid Status value Code 400 as per the API contract document
  #          Actual - status 200 Ok with empty response
      

  Scenario Outline: 05_Upload pet image 
		
	Given Pet already added to the store
	When User calls Uppload pet image API "PostImageAPI" with <petId>, <additionalMetadata>, <filePath>
	Then API call got success with status code 200
	And Validate all values and schema in the response body for upload image are as expected <code>, <type>, <additionalMetadata>, <filePath>

	Examples:
	|petId   	 		| additionalMetadata | filePath   | code | type 		|
	|1500		      | Pet is very cute   | dog1.jpg 	| 200  | unknown 	|
	
	
  # Upload pet image - Negative Test (NOT AUTOMATED DUE TO THE BELOW REASONS)
  # No negative test expected values provided by API contract document
  # Invalid Pet Id results in 500 internal server error.


	Scenario Outline: 06_Update an existing pet
		
	Given Pet already added to the store
	When User calls update pet API "UpdatePetAPI" with <petId>, <catId>, <catName>, <name>, <url>, <tagId>, <tagname>, <status>
	Then API call got success with status code 200
	And Validate all values and schema in the response body are as expected <petId>, <catId>, <catName>, <name>, <url>, <tagId>, <tagname>, <status>

	Examples:
	|petId 	 | catId |catName		   |name        |url			|tagId|tagname	  |status		   |
	|1500  	 | 1     |Black        |White Doggie|dog1.jpg	|10   |Tall Doggie|pending     |

  # Update an existing pet - Negative Test (NOT AUTOMATED DUE TO THE BELOW REASONS)
  # Defect1 - Expected - Invalid ID supplied Code 400 as per the API contract document
  #           Actual - getting 500 server error, while passing Pet id as alphabhet "PET" instead of integer
  # Defect2 - Expected - 	Pet not found Code 404 as per the API contract document
  #           Actual - getting 200 with succssful pet updates to the store
  # Defect1 - Expected - Validation exception Code 405 as per the API contract document
  #           Actual - getting 500 server error, while passing invalid data 

	Scenario Outline: 07_Update a pet in the store with form data
		
	Given Pet already added to the store
	When User calls update pet with form data API "UpdatePetFormDataAPI" with <petId>, <name>, <status>
	Then API call got success with status code 200
	And Validate all values and schema in the response body for update pet with form are as expected <code>, <type>, <petId>

	Examples:
	|petId 	 |name         |status		  | code | type 		| 
	|1500  	 |Yellow Doggie|sold        | 200  | unknown 	| 
	
	
	Scenario Outline: 08_Update a pet in the store with form data and invalid data
		
	Given Pet already added to the store
	When User calls update pet with form data API "UpdatePetFormDataAPI" with <petId>, <name>, <status>
	Then API call got success with status code 404 and error message <message>

	Examples:
	|petId 	 |name         |status		  | code | type 		| message 													| 
	|PET  	 |Yellow Doggie|sold        | 404  | unknown 	| java.lang.NumberFormatException		|
	|5647800 |Yellow Doggie|sold        | 404  | unknown 	| not found  												|


	Scenario Outline: 09_Delete a pet
		
	Given Pet already added to the store
	When User calls delete pet API "DeletePetAPI" with <petId>
	Then API call got success with status code 200
	And Validate all values and schema in the response body for update pet with form are as expected <code>, <type>, <petId>

	Examples:
	|petId 	 | code | type 	   	| 
	|1500  	 | 200  | unknown 	| 
	|1501  	 | 200  | unknown 	| 
	
	
	# Delete a pet - Negative Test (NOT AUTOMATED DUE TO THE BELOW REASONS)
  # Defect1 - Expected - Invalid ID supplied Code 400 as per the API contract document
  #           Actual - getting 404 Not found, while passing pet id which is not available in the store
		

	Scenario Outline: 10_Delete a pet with invalid data
		
	Given Pet already added to the store
	When User calls delete pet API "DeletePetAPI" with <petId>
	Then API call got success with status code 404 and error message <message>

	Examples:
	|petId 	 | code | type 	   	| message 											 |
	|PET  	 | 404  | unknown 	| java.lang.NumberFormatException|
	|null  	 | 404  | unknown 	| java.lang.NumberFormatException|
