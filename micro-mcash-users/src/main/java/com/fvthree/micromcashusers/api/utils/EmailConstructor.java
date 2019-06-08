package com.fvthree.micromcashusers.api.utils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fvthree.micromcashusers.api.model.WebUsers;

@Component
public class EmailConstructor {

	@Autowired
	private Environment env;

	@Autowired
	private TemplateEngine templateEngine;

	public MimeMessagePreparator constructNewUserEmail(WebUsers user, String password) {
		Context context = new Context();
		context.setVariable("user", user);
		context.setVariable("password", password);
		String text = templateEngine.process("newUserEmailTemplate", context);
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setPriority(1);
				email.setTo(user.getEmail());
				email.setSubject("Welcome To MCash");
				email.setText(text, true);
				email.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};
		return messagePreparator;
	}
}
