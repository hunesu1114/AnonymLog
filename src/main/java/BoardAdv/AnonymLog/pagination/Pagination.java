package BoardAdv.AnonymLog.pagination;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Pagination {
    public static int currentPage;
    public int totalPostCount;
    public int totalPageCount;
    public int postCntPerPage=PagingConst.POST_CNT_PER_PAGE;
    public int pageCntPerBlock=PagingConst.PAGE_CNT_PER_BLOCK;
    public int startPage;
    public int endPage;
    public int limitStart;
    public int prePageStartPage;
    public int nextPageStartPage;


    public Pagination(int totalPostCount, int currentPage) {
        if (totalPostCount > 0) {
            this.totalPostCount = totalPostCount;
        }
        this.currentPage = currentPage;
        calculation();
    }

    private void calculation() {

        totalPageCount = ((totalPostCount - 1) / postCntPerPage) + 1;

        startPage = ((currentPage - 1) / pageCntPerBlock) * pageCntPerBlock + 1;

        endPage = startPage + pageCntPerBlock - 1 > totalPageCount ? totalPageCount : startPage + pageCntPerBlock - 1;

        limitStart = (currentPage - 1) * postCntPerPage;

        prePageStartPage=(startPage==1)?1:startPage-5;

        nextPageStartPage = (endPage == totalPageCount) ? endPage : endPage + 1;

    }

    public List<Integer> pagesInCurrentBlock() {
        List<Integer> list = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            list.add(i);
        }
        return list;
    }
}
