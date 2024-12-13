package com.team3.camping.repository.userRepository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;

@Repository
public class UserSocialAccountDaoImpl implements UserSocialAccountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    // 소셜 정보 수정
	@Override
	public ResultInfo socialAccountUpdDate(UserInfoVo objUserInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
						                 .withProcedureName("USER_SOCIAL_UPD")
						                 .declareParameters(
						                   		            new SqlParameter("pi_strUserEmail",  Types.VARCHAR)
						                   		           ,new SqlParameter("pi_strUserBirth",  Types.DATE) 
						                   		           ,new SqlParameter("pi_strGender",     Types.VARCHAR) 
						                   		           ,new SqlParameter("pi_strUserTelNum", Types.VARCHAR)
						                   		           ,new SqlParameter("pi_strZipCode",    Types.VARCHAR)
						                   		           
						                   		           ,new SqlParameter("pi_strBaseAddr",   Types.VARCHAR) 
						                   		           ,new SqlParameter("pi_strDtlAddr",    Types.VARCHAR) 
						                   		            
						                   		           ,new SqlOutParameter("po_intRetVal",  Types.INTEGER)
						                    		       ,new SqlOutParameter("po_strRetVal",  Types.VARCHAR)
						                   		            );

			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strUserEmail",  objUserInfo.getUserEmail());
			inputParameter.put("pi_strUserBirth",  objUserInfo.getUserBirth());
			inputParameter.put("pi_strGender",     objUserInfo.getGender());
			inputParameter.put("pi_strUserTelNum", objUserInfo.getUserTelNum());
			inputParameter.put("pi_strZipCode",    objUserInfo.getZipCode());
			
			inputParameter.put("pi_strBaseAddr",   objUserInfo.getBaseAddr());
			inputParameter.put("pi_strDtlAddr",    objUserInfo.getDtlAddr());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);

		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9990);
			objResultInfo.setStrRetVal("Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}
}
