package com.team3.camping.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PagingUtil {

	private int currentPage;
	private int limit;
	private int totalPages;
	private int totalCount;
	
	private List<Integer>       pageRange;
	private List<UserInfoVo>    objUserList;
	private List<CampInfoVo>    objCampList;
	private List<HelpVo>        objHelpList;
	private List<ReviewVo>      objReviewList;
	
	private List<CartVo>        objCartList;
	private List<ReservationVo> objReservationList;
	
	public int getStartRow() {
        return (currentPage - 1) * limit + 1;
    }

    public int getEndRow() {
        return currentPage * limit;
    }

    public void calculateTotalPages(int totalCount) {
        this.totalPages = (int) Math.ceil((double) totalCount / limit);
    }
    
    public List<Integer> getPageRange() {
    	
        pageRange = new ArrayList<>();
        
        try {
        	int startPage = Math.max(1, currentPage - 5);        // 현재 페이지에서 5페이지 이전
        	int endPage   = Math.min(totalPages, startPage + 9); // 시작 페이지로부터 10페이지 범위
        	
        	if (endPage - startPage < 9) {
        		endPage = Math.min(totalPages, startPage + 9);
        	}
        	
        	for (int i = startPage; i <= endPage; i++) {
        		pageRange.add(i);
        	}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
        return pageRange;
    }
}
