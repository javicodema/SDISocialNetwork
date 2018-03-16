package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;

@Component
public class AddPostFormValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Post.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Post post = (Post) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "Error.empty");
		if (post.getTitle().length() > 30) {
			errors.rejectValue("title", "Error.title.long");
		}
		if (post.getMessage().length() > 180) {
			errors.rejectValue("message", "Error.msg.long");
		}
	}

}
