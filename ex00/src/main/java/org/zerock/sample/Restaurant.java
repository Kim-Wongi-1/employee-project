package org.zerock.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Data
@RequiredArgsConstructor
public class Restaurant {
	
//	@Autowired // 필드주입
//	@Setter(onMethod_ = @Autowired)
	private final Chef chef;
	
//	@Autowired // 세터주입
//	public void setChef(Chef chef) {
	
//		this.chef = chef;
//	}
}
