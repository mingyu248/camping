package com.team3.camping.service.userService;

import java.util.Map;

import com.team3.camping.domain.CartVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;

public interface UserCartService {
	
	// 찜 추가
	public ResultInfo addCart(CartVo objCart);
	// 찜 리스트
	public PagingUtil getCartList(Map<String, Object> cartPagingMap);
	// 찜 중복체크
    public Integer cartCheck(CartVo objCart);
    // 찜 삭제
    public ResultInfo cartDelete(CartVo objCart);
}
