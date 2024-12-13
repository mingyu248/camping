package com.team3.camping.service.userService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.team3.camping.domain.CartVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.repository.userRepository.UserCartDao;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserCartServiceImpl implements UserCartService {
	
	@Autowired
	private UserCartDao userCartDao;
	
	private final PlatformTransactionManager transactionManager;
    
    public UserCartServiceImpl(PlatformTransactionManager transactionManager) {
          
        this.transactionManager = transactionManager;
    }
    
    // 찜 중복체크
    @Override
    public Integer cartCheck(CartVo objCart) {
        
        return userCartDao.cartCheck(objCart);
    }
	
    // 찜 추가
	@Override
	public ResultInfo addCart(CartVo objCart) {
		
		TransactionStatus txStatusAddCart = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			ResultInfo objResultInfo = userCartDao.addCart(objCart);
			
			if (objResultInfo.getIntRetVal() != 0) {
				transactionManager.rollback(txStatusAddCart);
				
			} else {
				transactionManager.commit(txStatusAddCart);
			}
			return objResultInfo;
			
		} catch (Exception ex) {
			transactionManager.rollback(txStatusAddCart);
			log.info(ex.getMessage());

			return null;
		}
	}
	
	// 찜 리스트
	@Override
	public PagingUtil getCartList(Map<String, Object> cartPagingMap) {
	    
	    PagingUtil objPagingUtil = new PagingUtil();
        
        objPagingUtil.setCurrentPage((int)cartPagingMap.get("currentPage"));
        objPagingUtil.setLimit((int)cartPagingMap.get("limit"));
        
        try {
            cartPagingMap.put("startRow", objPagingUtil.getStartRow());
            cartPagingMap.put("endRow",   objPagingUtil.getEndRow());
	    
            List<CartVo> objCartList = userCartDao.getCartList(cartPagingMap);
            Integer intCartTotalCount = userCartDao.getCampTotalCount(cartPagingMap);
		
    	    if (objCartList == null || intCartTotalCount == null) return null;
    	    
    	    objPagingUtil.calculateTotalPages(intCartTotalCount);
    	    cartPagingMap.put("totalPages", objPagingUtil.getTotalPages());
    	    
    	    objPagingUtil.setObjCartList(objCartList);
    	    
    	    objPagingUtil.setPageRange(objPagingUtil.getPageRange());
    	    cartPagingMap.put("pageRange", objPagingUtil.getPageRange());
    	    
        } catch (Exception ex) {
            log.info(ex.getMessage());
            
            objPagingUtil = null;
        }
        return objPagingUtil;
	}
	
	// 찜 삭제
	@Override
	public ResultInfo cartDelete(CartVo objCart) {
		
	    ResultInfo objResultInfo = new ResultInfo();
        
        TransactionStatus txStatusCartDelete = transactionManager.getTransaction(new DefaultTransactionDefinition());
        
        try {
            Integer intRetVal = userCartDao.cartDelete(objCart);
            
            if (intRetVal == null || intRetVal != 1) {
                transactionManager.rollback(txStatusCartDelete);
                objResultInfo.setIntRetVal(5140);
                objResultInfo.setStrRetVal("찜 취소 실패");
                
            } else {
                transactionManager.commit(txStatusCartDelete);
                objResultInfo.setIntRetVal(0);
                objResultInfo.setStrRetVal("SUCCESS");
            }
        } catch (Exception ex) {
            transactionManager.rollback(txStatusCartDelete);
            log.info(ex.getMessage());
            objResultInfo.setIntRetVal(5140);
            objResultInfo.setStrRetVal("찜 취소 에러");
        }
        return objResultInfo;
	}
}
