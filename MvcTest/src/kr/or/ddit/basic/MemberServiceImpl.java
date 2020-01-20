package kr.or.ddit.basic;

import java.util.List;

public class MemberServiceImpl implements IMemberService {

	private IMemberDao memDao;
	
	public MemberServiceImpl() {
		memDao = new MemberDaoImpl();
	}
	
	
	
	@Override
	public int insertMember(MemberVO mv) {
		// TODO Auto-generated method stub
		return memDao.insertMember(mv);
	}

	@Override
	public boolean getMember(String memId) {
		// TODO Auto-generated method stub
		return memDao.getMember(memId);
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		// TODO Auto-generated method stub
		return memDao.getAllMemberList();
	}

	@Override
	public int updateMember(MemberVO mv) {
		// TODO Auto-generated method stub
		return memDao.updateMember(mv);
	}

	@Override
	public int deleteMember(String memId) {
		// TODO Auto-generated method stub
		return memDao.deleteMember(memId);
	}



	@Override
	public List<MemberVO> getSearchMember(MemberVO mv) {
		// TODO Auto-generated method stub
		return memDao.getSearchMember(mv);
	}

}
