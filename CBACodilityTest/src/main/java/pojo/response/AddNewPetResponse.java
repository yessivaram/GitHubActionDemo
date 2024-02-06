package pojo.response;

import java.util.List;

public class AddNewPetResponse {

	private Integer id;
	private CategoryResponse category;
	private String name;
	private List<String> photoUrls;
	private List<TagResponse> tags;
	private String status;

	public Integer getId() {
	return id;
	}

	public void setId(Integer id) {
	this.id = id;
	}

	public CategoryResponse getCategory() {
	return category;
	}

	public void setCategory(CategoryResponse category) {
	this.category = category;
	}

	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	public List<String> getPhotoUrls() {
	return photoUrls;
	}

	public void setPhotoUrls(List<String> photoUrls) {
	this.photoUrls = photoUrls;
	}

	public List<TagResponse> getTags() {
	return tags;
	}

	public void setTags(List<TagResponse> tags) {
	this.tags = tags;
	}

	public String getStatus() {
	return status;
	}

	public void setStatus(String status) {
	this.status = status;
	}

}