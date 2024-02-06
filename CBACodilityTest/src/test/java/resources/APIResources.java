package resources;

public enum APIResources {		

	AddPetAPI("/pet"),
	GetPetByIdAPI("/pet/{petid}"),
	GetPetByStatusAPI("/pet/findByStatus/"),
	PostImageAPI("/pet/{petid}/uploadImage"),
	UpdatePetAPI("/pet"),
	UpdatePetFormDataAPI("/pet/{petid}"),
	DeletePetAPI("/pet/{petid}");
	
	private String resource;
	
	APIResources(String resource)
	{
		this.resource=resource;
	}
	
	public String getResource()
	{
		return resource;
	}
}
