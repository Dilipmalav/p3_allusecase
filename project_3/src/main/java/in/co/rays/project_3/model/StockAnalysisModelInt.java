package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.StockAnalysisDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface StockAnalysisModelInt {

	public long add(StockAnalysisDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(StockAnalysisDTO dto)throws ApplicationException;
	public void update(StockAnalysisDTO dto)throws ApplicationException,DuplicateRecordException;
	public StockAnalysisDTO findByPK(long pk)throws ApplicationException;
	public StockAnalysisDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(StockAnalysisDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(StockAnalysisDTO dto)throws ApplicationException;
	
}
