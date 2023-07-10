package com.hjm.greengram.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.hjm.greengram.domain.image.Image;
import com.hjm.greengram.domain.user.User;

import lombok.Data;

@Data
public class ImageUploadDto {
	
	private MultipartFile file;
	private String caption;
	
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUrl)
				.user(user)
				.build();
	}
}
