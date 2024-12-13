package com.team3.camping.repository.userRepository;

import java.util.List;
import java.util.Map;

import com.team3.camping.domain.CartVo;
import com.team3.camping.domain.ResultInfo;


public interface UserCartDao {
	
	// 찜 추가
	public ResultInfo addCart(CartVo objCart);
	// 찜 리스트
	public List<CartVo> getCartList(Map<String, Object> cartPagingMap);
	// 찜 중복 체크
    public Integer cartCheck(CartVo objCart);
    // 찜 삭제
    public Integer cartDelete(CartVo objCart);
    // 카트 총 갯수
    public Integer getCampTotalCount(Map<String, Object> cartPagingMap);
}
