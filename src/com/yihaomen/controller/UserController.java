package com.yihaomen.controller;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yihaomen.inter.IUserOperation;
import com.yihaomen.model.Article;
import com.yihaomen.util.PageInfo;

@Controller
@RequestMapping("/article")
public class UserController {
	@Autowired
	IUserOperation userMapper;
	@Autowired
	IUserOperation iUserOperation;

	@RequestMapping("/list")
	public ModelAndView listall(HttpServletRequest request,HttpServletResponse response){
		List<Article> articles=userMapper.getUserArticles(1); 
		ModelAndView mav=new ModelAndView("list");
		mav.addObject("articles",articles);
		return mav;
	}
	@RequestMapping("/pagelist")
    public ModelAndView pageList(HttpServletRequest request,HttpServletResponse response){
        int currentPage = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
        int pageSize = 3;
        if (currentPage<=0){
            currentPage =1;
        }
        int currentResult = (currentPage-1) * pageSize;
        
        //System.out.println(request.getRequestURI());
        //System.out.println(request.getQueryString());
        
        PageInfo page = new PageInfo();
        page.setShowCount(pageSize);
        page.setCurrentResult(currentResult);
        List<Article> articles=iUserOperation.selectArticleListPage(page,1);
        //System.out.println(page);
        int lastPage=0;
        

        
        //自己加的分页
        int bef=0;
        int end2=pageSize;boolean b=false;
        if(currentPage!=1){
        	//1 3   2 36  3 69
        	//03 33 63 93 123 153
        	bef=(currentPage-1)*pageSize;
        	end2=pageSize;
        }
        List<Article> articlesPage=iUserOperation.getUserArticlesPage(1,bef,end2);
        int total = iUserOperation.getTotal(1);
        if (total % pageSize==0){
            lastPage = total % pageSize;
        }
        else{
            lastPage =1+ total / pageSize;
        }
        if (currentPage>=lastPage)b=true;
        
        

        int totalCount = page.getTotalResult();
        
        if (totalCount % pageSize==0){
            lastPage = totalCount % pageSize;
        }
        else{
            lastPage =1+ totalCount / pageSize;
        }
        
        String pageStr = "";
        int begin=currentPage-1;
        int end=currentPage+1;
        
        if (currentPage>=lastPage){
            currentPage =lastPage;
            end=end-1;
        }
        
        
        pageStr=String.format("<a href=\"%s\">上一页</a>    <a href=\"%s\">下一页</a>",
                        request.getRequestURI()+"?page="+(begin),request.getRequestURI()+"?page="+(end) );


        //制定视图，也就是list.jsp
        ModelAndView mav=new ModelAndView("list");
        mav.addObject("articles",articles);
        mav.addObject("articlesPage",articlesPage);
        mav.addObject("pageStr",pageStr);
        if (currentPage>=lastPage)mav.addObject("msg","已是最后一页");
        if (b)mav.addObject("msg2","自己写的分页：已是最后一页");
        return mav;
    }
}
