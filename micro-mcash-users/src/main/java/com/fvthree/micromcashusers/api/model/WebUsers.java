package com.fvthree.micromcashusers.api.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebUsers implements Serializable {
	
	private Long userid;
	private String username;
	private String password;
	private String email;
	private Date created;
	private Date updated;
}
