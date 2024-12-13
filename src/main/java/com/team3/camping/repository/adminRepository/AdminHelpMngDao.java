package com.team3.camping.repository.adminRepository;

import java.util.List;

import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.ResultInfo;

public interface AdminHelpMngDao {

	// 문의 답변
	public ResultInfo adminInsertHelpAnswer(HelpVo objHelpVo);
	// 문의 목록
	public List<HelpVo> getHelpList(String strHelpTitle, String strUserId, String strStartDate, String strEndDate, Integer intStateCode, int intStartRow, int intEndRow);
	// 문의 글 수
	public int getHelpTotalCount(String strHelpTitle, String strUserId, String strStartDate, String strEndDate, Integer intStateCode);
	// 문의 상세
	public HelpVo getUserHelpDetail(int intHelpNo);
	// 문의 상태별 개수
	public List<HelpVo> getHelpCountByStatus();
	
	
	// 문의 삭제
	public ResultInfo adminDeleteUserHelp(int intHelpNo);
}
