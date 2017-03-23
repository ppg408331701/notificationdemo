package webapps.MOrangeCheck.com.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;

import ppg.com.yanlibrary.widget.TopBarLayout;
import webapps.MOrangeCheck.com.Fragment.Company.CompanyDocument.CompanyDocument;
import webapps.MOrangeCheck.com.Fragment.Company.CompanyHomePage;
import webapps.MOrangeCheck.com.Fragment.Company.Examine.CommonTimeApply;
import webapps.MOrangeCheck.com.Fragment.Company.Examine.Examine;
import webapps.MOrangeCheck.com.Fragment.Company.Examine.ExamineBasePag;
import webapps.MOrangeCheck.com.Fragment.Company.Examine.ExamineItemDetail;
import webapps.MOrangeCheck.com.Fragment.Company.Meber.Meber;
import webapps.MOrangeCheck.com.Fragment.Company.Report.Arrange;
import webapps.MOrangeCheck.com.Fragment.Company.Report.ChoiceMonth;
import webapps.MOrangeCheck.com.Fragment.Company.Report.Rank.Rank;
import webapps.MOrangeCheck.com.Fragment.Company.Report.Report;
import webapps.MOrangeCheck.com.Fragment.Company.Report.ReportItemDetatil;
import webapps.MOrangeCheck.com.Fragment.Company.Report.ReportMore;
import webapps.MOrangeCheck.com.Fragment.Company.WorkOutside.ChoiceSite;
import webapps.MOrangeCheck.com.Fragment.Company.WorkOutside.SearchSite;
import webapps.MOrangeCheck.com.Fragment.Company.WorkOutside.WorkOutSide;
import webapps.MOrangeCheck.com.Fragment.MeetingPlace.Dynamic.Dynamic;
import webapps.MOrangeCheck.com.Fragment.MeetingPlace.MeetingPlace;
import webapps.MOrangeCheck.com.Fragment.MeetingPlace.SmartNavigation.NagationSetting;
import webapps.MOrangeCheck.com.Fragment.MeetingPlace.SmartNavigation.SmartNaigation;
import webapps.MOrangeCheck.com.Fragment.Personal.MyInfo.CompleteMyInfo;
import webapps.MOrangeCheck.com.Fragment.Personal.MyInfo.MyInfo;
import webapps.MOrangeCheck.com.Fragment.Personal.MyInfo.MyPage;
import webapps.MOrangeCheck.com.Fragment.Personal.PersonalInfo;
import webapps.MOrangeCheck.com.R;

/**
 * Created by ppg on 2016/9/27.
 */

public class DetailActivity extends BaseActivity {

    //公司部分
    public final static int FRAGMENT_DEVICEFRAGMENT = 1;//设备管理
    public final static int FRAGMENT_COMPANYHOMEPAGE = 2;//公司主页
    public final static int FRAGMENT_SETTINGFRAGMENT = 4;//设置
    public final static int FRAGMENT_REPORTFRAGMENT = 5;//报表
    public final static int FRAGMENT_ARRANGE = 6;//排班
    public final static int FRAGMENT_CHOICEMONTH = 8;//选择月份
    public final static int FRAGMENT_REPORTITEMDETATIL = 9;//报表每个item的详情页
    public final static int FRAGMENT_REPORTDETAIL = 10;//报表查看详情
    public final static int FRAGMENT_EXAMINE = 11;//审核/申请
    public final static int FRAGMENT_EXAMINEBASEPAG = 12;//审核/申请/抄送-子列表页面
    public final static int FRAGMENT_COMMONAPPLY = 13;//通用审核
    public final static int FRAGMENT_EXAMINEITEMDETAIL = 14;//申请,审批,抄送子列表的item的详情
    public final static int FRAGMENT_RANK = 15;//通用审核
    public final static int FRAGMENT_WORKOUTSIDE = 16;//外勤
    public final static int FRAGMENT_MEBER = 17;//成员
    public final static int FRAGMENT_CHOICESITE = 18;//外勤-选择地点
    public final static int FRAGMENT_SEARCHSITE = 19;//外勤-选择地点-搜索
    public final static int FRAGMENT_COMPANYDOCUMENT = 20;//公司文档


    //会议部分
    public final static int FRAGMENT_MEETINGPLACEFRAGMENT = 100;//会议主页
    public final static int FRAGMENT_SMARTNAIGATION = 101;//智能导览
    public final static int FRAGMENT_NAGATIONSETTING = 102;//只能导览设定
    public final static int FRAGMENT_DYNAMIC = 103;//只能导览设定


    //个人部分
    public final static int FRAGMENT_PERSONAL = 200;//个人信息
    public final static int FRAGMENT_MYPAGE = 201;//我的
    public final static int FRAGMENT_MYINFO = 202;//我的信息
    public final static int FRAGMENT_COMPLETEMYINFO = 203;//完善我的信息


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreateView(TopBarLayout topBar) {
        showTopBarOnlyBack();
        SetTitleTextColor(ContextCompat.getColor(this, R.color.black0));
    }

    @Override
    protected void installSimpleFragment(int index) {
        switch (index) {
            case FRAGMENT_COMPANYHOMEPAGE:
                replaceFragment(new CompanyHomePage());
                break;
            case FRAGMENT_REPORTFRAGMENT:
                replaceFragment(new Report());
                break;
            case FRAGMENT_ARRANGE:
                replaceFragment(new Arrange());
                break;
            case FRAGMENT_CHOICEMONTH:
                replaceFragment(new ChoiceMonth());
                break;
            case FRAGMENT_REPORTITEMDETATIL:
                replaceFragment(new ReportItemDetatil());
                break;
            case FRAGMENT_REPORTDETAIL:
                replaceFragment(new ReportMore());
                break;
            case FRAGMENT_MEETINGPLACEFRAGMENT:
                replaceFragment(new MeetingPlace());
                break;
            case FRAGMENT_EXAMINE:
                replaceFragment(new Examine());
                break;
            case FRAGMENT_EXAMINEBASEPAG:
                replaceFragment(new ExamineBasePag());
                break;
            case FRAGMENT_COMMONAPPLY:
                replaceFragment(new CommonTimeApply());
                break;
            case FRAGMENT_EXAMINEITEMDETAIL:
                replaceFragment(new ExamineItemDetail());
                break;
            case FRAGMENT_SMARTNAIGATION:
                replaceFragment(new SmartNaigation());
                break;
            case FRAGMENT_NAGATIONSETTING:
                replaceFragment(new NagationSetting());
                break;
            case FRAGMENT_RANK:
                replaceFragment(new Rank());
                break;
            case FRAGMENT_WORKOUTSIDE:
                replaceFragment(new WorkOutSide());
                break;
            case FRAGMENT_MEBER:
                replaceFragment(new Meber());
                break;
            case FRAGMENT_PERSONAL:
                replaceFragment(new PersonalInfo());
                break;
            case FRAGMENT_MYPAGE:
                replaceFragment(new MyPage());
                break;
            case FRAGMENT_MYINFO:
                replaceFragment(new MyInfo());
                break;
            case FRAGMENT_COMPLETEMYINFO:
                replaceFragment(new CompleteMyInfo());
                break;
            case FRAGMENT_DYNAMIC:
                replaceFragment(new Dynamic());
                break;
            case FRAGMENT_CHOICESITE:
                replaceFragment(new ChoiceSite());
                break;
            case FRAGMENT_SEARCHSITE:
                replaceFragment(new SearchSite());
                break;
            case FRAGMENT_COMPANYDOCUMENT:
                replaceFragment(new CompanyDocument());
                break;
        }

    }


}
