package report;

import java.util.List;

public class BoardServiceImpl implements IBoardService {

	private IBoardDao memDao;
	
	public BoardServiceImpl() {
		memDao = new BoardDaoImpl();
	}
	
	
	
	@Override
	public int insertBoard(BoardVO bv) {
		// TODO Auto-generated method stub
		return memDao.insertBoard(bv);
	}

	

	@Override
	public List<BoardVO> displayBoardAll() {
		// TODO Auto-generated method stub
		return memDao.displayBoardAll();
	}

	@Override
	public int updateBoard(BoardVO bv) {
		// TODO Auto-generated method stub
		return memDao.updateBoard(bv);
	}

	@Override
	public int deleteBoard(String board_title) {
		// TODO Auto-generated method stub
		return memDao.deleteBoard(board_title);
	}



	@Override
	public List<BoardVO> search(BoardVO bv) {
		// TODO Auto-generated method stub
		return memDao.search(bv);
	}

}
