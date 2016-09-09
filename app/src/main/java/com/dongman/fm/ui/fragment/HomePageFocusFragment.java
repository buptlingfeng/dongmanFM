package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongman.fm.R;
import com.dongman.fm.data.RelativeRecommend;
import com.dongman.fm.ui.activity.GroupDetailActivity;
import com.dongman.fm.ui.activity.SearchActivity;
import com.dongman.fm.ui.fragment.adapter.HomePageFocusFragmentAdapter;
import com.dongman.fm.ui.fragment.adapter.RelativeAdapter;
import com.dongman.fm.ui.view.Banner;
import com.dongman.fm.ui.view.BannerView;
import com.dongman.fm.ui.view.ManTuanHomeItemView;
import com.dongman.fm.ui.view.SpacesItemDecoration;
import com.dongman.fm.ui.view.TopicHomeItemView;
import com.dongman.fm.utils.ImageUtils;
import com.dongman.fm.utils.ToolsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by liuzhiwei on 15/11/14.
 */
public class HomePageFocusFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = HomePageFocusFragmentAdapter.class.getSimpleName();

    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;

    private Context mContext;

    private RecyclerView mAnimesRecycleView;
    private RelativeAdapter mAnimesAdapter;

    private ManTuanHomeItemView mMantuan1, mMantuan2, mMantuan3, mMantuan4, mMantuan5, mMantuan6;
    private TopicHomeItemView mTopic1, mTopic2, mTopic3;

    private View mSearchView;


    private String testAnimesData = "[{\"id\":\"18038\",\"title\":\"亡国的阿基德最终章：致我所爱\",\"subtype\":\"movie\",\"year\":\"2016\",\"rate_average\":7.4,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/3346e16b9586ff31651f38af68e19709.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"15735\",\"title\":\"怪物之子\",\"subtype\":\"movie\",\"year\":\"2015\",\"rate_average\":8.6,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/c58ae60071ce45ec0f7e819a7d63138d.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"17719\",\"title\":\"火影忍者 剧场版11 博人\",\"subtype\":\"movie\",\"year\":\"2015\",\"rate_average\":8.5,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/51c087abb28d6629ab1907c7d54d601a.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"16369\",\"title\":\"虫师 铃之滴\",\"subtype\":\"movie\",\"year\":\"2015\",\"rate_average\":9.8,\"score\":90,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/9d2a44b4660574dd655da61762acab17.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"13455\",\"title\":\"哆啦A梦：伴我同行\",\"subtype\":\"movie\",\"year\":\"2015\",\"rate_average\":8.8,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/15bb30fc97099a7e579e5a61355cf76d.jpg\",\"is_end\":\"0\",\"play_count_str\":\"更新至1集\"},{\"id\":\"7164\",\"title\":\"约会大作战 万由里裁决\",\"subtype\":\"movie\",\"year\":\"2015\",\"rate_average\":8.7,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/public\\/dabdf61ea5d6f86ce45567065a0a5672.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"15749\",\"title\":\"尸者的帝国\",\"subtype\":\"movie\",\"year\":\"2015\",\"rate_average\":7.8,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/822b709518bfe96d2d172e6eade2d5cc.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"15747\",\"title\":\"境界的彼方 未来篇\",\"subtype\":\"movie\",\"year\":\"2015\",\"rate_average\":9,\"score\":90,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/445695ca7b73fff1f873b260f8d81ef9.jpg\",\"is_end\":\"0\",\"play_count_str\":\"更新至0集\"},{\"id\":\"6477\",\"title\":\"心理测量者 剧场版\",\"subtype\":\"movie\",\"year\":\"2015\",\"rate_average\":8.5,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/304fe69918608d14f5581127f1237f0f.jpg\",\"is_end\":\"0\",\"play_count_str\":\"更新至0集\"},{\"id\":\"16472\",\"title\":\"翠星之加尔刚蒂亚：继续巡游的航路，向着远方(前篇)\",\"subtype\":\"movie\",\"year\":\"2014\",\"rate_average\":6.8,\"score\":60,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/8244df5425a5a94c59914219050b6e3c.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"6742\",\"title\":\"玉子爱情故事\",\"subtype\":\"movie\",\"year\":\"2014\",\"rate_average\":9.3,\"score\":90,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/0c809f8c190fad6fc7c569d7a3cd2457.jpg\",\"is_end\":\"0\",\"play_count_str\":\"更新至1集\"},{\"id\":\"239\",\"title\":\"乐园追放\",\"subtype\":\"movie\",\"year\":\"2014\",\"rate_average\":6.5,\"score\":60,\"img_url\":\"http:\\/\\/img.dongman.fm\\/public\\/b9fed7910197898f05e9925735be2223.jpg\",\"is_end\":\"0\",\"play_count_str\":\"更新至1集\"},{\"id\":\"101\",\"title\":\"圣斗士星矢：圣域传说\",\"subtype\":\"movie\",\"year\":\"2014\",\"rate_average\":7.6,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/6f504150637d84a38c6b81ba7d8cae23.jpg\",\"is_end\":\"0\",\"play_count_str\":\"更新至1集\"},{\"id\":\"4266\",\"title\":\"春\",\"subtype\":\"movie\",\"year\":\"2013\",\"rate_average\":7.8,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/856356f611d6dcd79d09ce3a0e7b3052.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"470\",\"title\":\"花开伊吕波剧场版：甜蜜的家\",\"subtype\":\"movie\",\"year\":\"2013\",\"rate_average\":8.7,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/8de95263b3ce12493bbe4ed41314002c.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"4806\",\"title\":\"船长哈洛克\",\"subtype\":\"movie\",\"year\":\"2013\",\"rate_average\":6.9,\"score\":60,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/0d818082b39866481b88f752b81abd90.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"3\",\"title\":\"颠倒的帕特玛\",\"subtype\":\"movie\",\"year\":\"2013\",\"rate_average\":8.3,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/1f3b7f6957287ae6b8ae97eecf5f80bc.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"9802\",\"title\":\"辉夜姬物语\",\"subtype\":\"movie\",\"year\":\"2013\",\"rate_average\":8.3,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/e6481ecbb119bee1b7eace08e78d71e0.jpg\",\"is_end\":\"0\",\"play_count_str\":\"更新至0集\"},{\"id\":\"4341\",\"title\":\"言语之庭\",\"subtype\":\"movie\",\"year\":\"2013\",\"rate_average\":8.4,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/24273d219cbe4e9d9be19ca1e5ff04eb.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全0集\"},{\"id\":\"208\",\"title\":\"全职猎人剧场版：绯色的幻影\",\"subtype\":\"movie\",\"year\":\"2013\",\"rate_average\":5.5,\"score\":50,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/b5bff5cd6ae1f3ba01ee171529f23a14.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"140\",\"title\":\"命运石之门：横行跋扈的浪荡之徒\",\"subtype\":\"movie\",\"year\":\"2012\",\"rate_average\":9.1,\"score\":90,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/b9e6a4dacb7d1b222eabfc552a3ff6bb.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"427\",\"title\":\"被狙击的学园\",\"subtype\":\"movie\",\"year\":\"2012\",\"rate_average\":7.1,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/dfcd2c6994afb2739962f3473f4c6008.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"12691\",\"title\":\"青之驱魔师 剧场版\",\"subtype\":\"movie\",\"year\":\"2012\",\"rate_average\":8,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/9cdf8cbbd4deb6f5dee467d5d1bcab5b.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"276\",\"title\":\"剧场版魔法少女小圆 后篇 永远的故事\",\"subtype\":\"movie\",\"year\":\"2012\",\"rate_average\":8.9,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/96538bf8b0aa07f60d09d8827d2a91f5.jpg\",\"is_end\":\"0\",\"play_count_str\":\"更新至0集\"},{\"id\":\"11461\",\"title\":\"剑风传奇 黄金时代篇1：霸王之卵\",\"subtype\":\"movie\",\"year\":\"2012\",\"rate_average\":8.5,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/b9727bf6a9ed7b01af3e4cbfd4414a92.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"12847\",\"title\":\"狼的孩子雨和雪\",\"subtype\":\"movie\",\"year\":\"2012\",\"rate_average\":8.6,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/723468d6f126fb4f02c14d0fba5f1207.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全0集\"},{\"id\":\"436\",\"title\":\"阿修罗\",\"subtype\":\"movie\",\"year\":\"2012\",\"rate_average\":7.9,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/359ebc3b160831e1952dae4f428c7f62.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"10305\",\"title\":\"追逐繁星的孩子\",\"subtype\":\"movie\",\"year\":\"2011\",\"rate_average\":7.2,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/46572a78bbf10e3e6690b74a775e8a39.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"11042\",\"title\":\"对某飞行员的追忆\",\"subtype\":\"movie\",\"year\":\"2011\",\"rate_average\":7.6,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/58a11a136873ae62b367137a258692a1.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"11385\",\"title\":\"虞美人盛开的山坡\",\"subtype\":\"movie\",\"year\":\"2011\",\"rate_average\":7.7,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/2a2dd312208f96f9aab2dd3235fca9b0.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"12215\",\"title\":\"给桃子的信\",\"subtype\":\"movie\",\"year\":\"2011\",\"rate_average\":8,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/e6152cb1797cd90e11ef28045f262688.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"12178\",\"title\":\"萤火之森\",\"subtype\":\"movie\",\"year\":\"2011\",\"rate_average\":9,\"score\":90,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/d23682193dcd1cd06d4c53c9161f7ad4.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"10979\",\"title\":\"古城荆棘王\",\"subtype\":\"movie\",\"year\":\"2010\",\"rate_average\":7.6,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/5e600ea3f52570d2e75c515ab6e1ef64.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"9643\",\"title\":\"文学少女 剧场版\",\"subtype\":\"movie\",\"year\":\"2010\",\"rate_average\":7,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/d75e2dc862eb18d706750fe3d7db5de1.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"10996\",\"title\":\"意外的幸运签\",\"subtype\":\"movie\",\"year\":\"2010\",\"rate_average\":8.2,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/878a5efcabfea92b3e01172c0ea75742.jpg\",\"is_end\":\"0\",\"play_count_str\":\"更新至0集\"},{\"id\":\"11445\",\"title\":\"空之境界 终章 空之境界\",\"subtype\":\"movie\",\"year\":\"2010\",\"rate_average\":8.4,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/bb70f644254753603839a9eb4447af1d.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"13951\",\"title\":\"夏娃的时间\",\"subtype\":\"movie\",\"year\":\"2010\",\"rate_average\":8.4,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/19aa4ecffef8ea491716ea3183a003d0.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"9859\",\"title\":\"银魂剧场版：新译红樱篇\",\"subtype\":\"movie\",\"year\":\"2010\",\"rate_average\":9.2,\"score\":90,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/cacf07c4f293e1e975b0f0a2f27d776d.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"9497\",\"title\":\"红线\",\"subtype\":\"movie\",\"year\":\"2010\",\"rate_average\":8.5,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/46ad1df9280f3b726990fcd02109d3d1.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"10721\",\"title\":\"海贼王 第0话 特别篇：强者世界前传\",\"subtype\":\"special\",\"year\":\"2010\",\"rate_average\":8.7,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/941e70b539e393134948089c491bc013.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"9825\",\"title\":\"枪神 剧场版\",\"subtype\":\"special\",\"year\":\"2010\",\"rate_average\":8.4,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/d46c9ee5753cd335cdf04363e0494620.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"10764\",\"title\":\"你看起来好像很好吃\",\"subtype\":\"movie\",\"year\":\"2010\",\"rate_average\":9,\"score\":90,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/c1d73c60ce1395b674f0dab72e8da3fa.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"10195\",\"title\":\"借东西的小人阿莉埃蒂\",\"subtype\":\"movie\",\"year\":\"2010\",\"rate_average\":8.8,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/7cc5b9b0c3ae8d0cf4267d0d061f5916.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"9242\",\"title\":\"弃宝之岛：遥与魔法镜\",\"subtype\":\"movie\",\"year\":\"2009\",\"rate_average\":7.9,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/0631b9f018c1a765d2383579e883b96f.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"9728\",\"title\":\"空想新子和千年的魔法\",\"subtype\":\"movie\",\"year\":\"2009\",\"rate_average\":7.6,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/f6f6b974025d6c6de10c6e9bd59cd313.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"9417\",\"title\":\"东之伊甸剧场版1：伊甸之王\",\"subtype\":\"movie\",\"year\":\"2009\",\"rate_average\":7.2,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/838aed2787318f0c60031bc5fb193f96.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"9502\",\"title\":\"夏日大作战\",\"subtype\":\"movie\",\"year\":\"2009\",\"rate_average\":8.8,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/7a6aeca58859f237febd997656f37cfd.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全0集\"},{\"id\":\"16482\",\"title\":\"超时空要塞F:虚空歌姬\",\"subtype\":\"movie\",\"year\":\"2009\",\"rate_average\":7.7,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/c420eb2eaea6e80b1efdf0ed1a64d809.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全0集\"},{\"id\":\"3477\",\"title\":\"悬崖上的金鱼姬\",\"subtype\":\"movie\",\"year\":\"2008\",\"rate_average\":8.2,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/30b7260238acd304f49588b8077ecb61.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"4890\",\"title\":\"空中杀手\",\"subtype\":\"movie\",\"year\":\"2008\",\"rate_average\":8,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/c9bfeed13be0b353c41d411c0a0b8c49.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"8244\",\"title\":\"天元突破剧场版：红莲篇\",\"subtype\":\"movie\",\"year\":\"2008\",\"rate_average\":8.5,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/543ed862cab1884beba71046a9c4c351.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"5520\",\"title\":\"钢琴之森\",\"subtype\":\"movie\",\"year\":\"2007\",\"rate_average\":7.8,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/d1a03d23434fead4dd5e0de860a1d4af.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"5450\",\"title\":\"异邦人：无皇刃谭\",\"subtype\":\"movie\",\"year\":\"2007\",\"rate_average\":8.3,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/78f1a44358d72822986e2c4d285f9700.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"7345\",\"title\":\"河童之夏\",\"subtype\":\"movie\",\"year\":\"2007\",\"rate_average\":8.7,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/35f590ee65836971b1e5272e14e47c66.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"5842\",\"title\":\"死神剧场版：钻石星尘的反叛 另一个冰轮丸\",\"subtype\":\"movie\",\"year\":\"2007\",\"rate_average\":7.2,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/4779e36abd8226b67242c22a60c9661e.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全0集\"},{\"id\":\"4255\",\"title\":\"秒速5厘米\",\"subtype\":\"tv\",\"year\":\"2007\",\"rate_average\":8.5,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/0116209bf83a7727eea68f73aada0300.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全3集\"},{\"id\":\"4072\",\"title\":\"2077日本锁国\",\"subtype\":\"movie\",\"year\":\"2007\",\"rate_average\":7.2,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/50fdd7c21f8b3d315198a94b086037af.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1861\",\"title\":\"银发的阿基多\",\"subtype\":\"movie\",\"year\":\"2006\",\"rate_average\":6.7,\"score\":60,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/69dc89cf55730055af485380feacc411.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"2524\",\"title\":\"勇者传说\",\"subtype\":\"movie\",\"year\":\"2006\",\"rate_average\":7.2,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/2755c302d89ae9a0371edb99793d2626.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全46集\"},{\"id\":\"2642\",\"title\":\"恶童\",\"subtype\":\"movie\",\"year\":\"2006\",\"rate_average\":8.6,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/12383fb33e2a1bc7f8697081c0235bd9.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"3233\",\"title\":\"穿越时空的少女\",\"subtype\":\"movie\",\"year\":\"2006\",\"rate_average\":8.7,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/d494dd3e3e5490490f6b111f16602124.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"2060\",\"title\":\"翡翠森林：狼与羊\",\"subtype\":\"movie\",\"year\":\"2005\",\"rate_average\":8.2,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/3a190fd3cd5bd44536d84eb523546cac.jpg\",\"is_end\":\"0\",\"play_count_str\":\"更新至0集\"},{\"id\":\"1699\",\"title\":\"最终幻想7：圣子降临\",\"subtype\":\"movie\",\"year\":\"2005\",\"rate_average\":8.2,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/96958b0be7b580c73a136e096c0beb8f.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1866\",\"title\":\"钢之炼金术师：香巴拉的征服者\",\"subtype\":\"movie\",\"year\":\"2005\",\"rate_average\":8.1,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/04c4cc03fca56220bf9830767275a556.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"924\",\"title\":\"攻壳机动队2 无罪\",\"subtype\":\"movie\",\"year\":\"2004\",\"rate_average\":9,\"score\":90,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/ffbb701ea327fab2e3be52bb79d8d073.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1586\",\"title\":\"云的彼端，约定的地方\",\"subtype\":\"movie\",\"year\":\"2004\",\"rate_average\":7.9,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/c7b10bac2b8a4825c49f6c357217385c.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1512\",\"title\":\"哈尔的移动城堡\",\"subtype\":\"movie\",\"year\":\"2004\",\"rate_average\":8.8,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/102816d5a83d3acb4ca303a938768471.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"931\",\"title\":\"蒸汽男孩\",\"subtype\":\"movie\",\"year\":\"2004\",\"rate_average\":7.6,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/585788acbd3cb1d98d3678f22c9d3281.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"3382\",\"title\":\"落叶\",\"subtype\":\"movie\",\"year\":\"2004\",\"rate_average\":8.7,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/7203dc846563f52e5819e0ab29ddbf1d.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"2000\",\"title\":\"天下霸道之剑\",\"subtype\":\"movie\",\"year\":\"2003\",\"rate_average\":8.4,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/4976f0b19086aad7864c69023fcab642.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1572\",\"title\":\"东京教父\",\"subtype\":\"movie\",\"year\":\"2003\",\"rate_average\":8.7,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/7889b942a1ad2b808f05c4c3c6d5b74d.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1373\",\"title\":\"猫的报恩\",\"subtype\":\"movie\",\"year\":\"2002\",\"rate_average\":7.9,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/3a058337e4aad90fddebeae1580a7ac5.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1449\",\"title\":\"千年女优\",\"subtype\":\"movie\",\"year\":\"2001\",\"rate_average\":8.4,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/08ffdc4021d6af2435021910ceba8692.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"921\",\"title\":\"千与千寻\",\"subtype\":\"movie\",\"year\":\"2001\",\"rate_average\":9.2,\"score\":90,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/4733f1ecd0247b1e5af44210970d48bd.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"2336\",\"title\":\"浪客剑心：星霜篇\",\"subtype\":\"movie\",\"year\":\"2001\",\"rate_average\":8.2,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/30defd67afb5a1045fea3ff50b01072c.jpg\",\"is_end\":\"0\",\"play_count_str\":\"更新至4集\"},{\"id\":\"925\",\"title\":\"大都会\",\"subtype\":\"movie\",\"year\":\"2001\",\"rate_average\":8.3,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/d7255c2f50c07fbf98cbedf4f91d7b40.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1438\",\"title\":\"星际牛仔：天国之门\",\"subtype\":\"movie\",\"year\":\"2001\",\"rate_average\":8.9,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/a9507d1e2ba8124fce4205f9a4738336.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1436\",\"title\":\"吸血鬼猎人D\",\"subtype\":\"movie\",\"year\":\"2000\",\"rate_average\":8.7,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/5ca35335815b4566c66529daa236f381.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"2340\",\"title\":\"我的女神 剧场版\",\"subtype\":\"movie\",\"year\":\"2000\",\"rate_average\":7.8,\"score\":70,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/0d64ec07179101cd7bf85bcbc19a3475.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1411\",\"title\":\"人狼\",\"subtype\":\"movie\",\"year\":\"1999\",\"rate_average\":8.6,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/86bb08da16a716c0a501689315758f9a.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1596\",\"title\":\"未麻的部屋\",\"subtype\":\"movie\",\"year\":\"1998\",\"rate_average\":8.7,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/3c4f6c5988dbcb07bb9f30e35f4b5613.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1808\",\"title\":\"名侦探柯南：引爆摩天楼\",\"subtype\":\"movie\",\"year\":\"1997\",\"rate_average\":8.3,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/4123effab7afed68b9d5d519f141b38d.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1170\",\"title\":\"幽灵公主\",\"subtype\":\"movie\",\"year\":\"1997\",\"rate_average\":8.7,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/b37fa5d61477bfd83707440fed6150fb.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"94\",\"title\":\"新机动战记高达W 无尽的华尔兹OVA\",\"subtype\":\"ova\",\"year\":\"1997\",\"rate_average\":8.5,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/5bb38afabfdf8bf62e530e38352e86f5.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全4集\"},{\"id\":\"7725\",\"title\":\"城市猎人剧场版 再见，我的爱人\",\"subtype\":\"movie\",\"year\":\"1997\",\"rate_average\":8.6,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/3b7a0c49d6b1d67d7f0b9eb64b3750b6.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1958\",\"title\":\"佛兰德斯的狗\",\"subtype\":\"movie\",\"year\":\"1997\",\"rate_average\":8.9,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/9367000fd7fded3df68002cfa7b09ba5.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"948\",\"title\":\"红猪\",\"subtype\":\"movie\",\"year\":\"1992\",\"rate_average\":8.3,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/c29e372fdf91b0b7a09d322f3f6b308b.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1476\",\"title\":\"老人Z\",\"subtype\":\"movie\",\"year\":\"1991\",\"rate_average\":8.3,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/4554fef1df6a47399e984ee6000c9290.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"943\",\"title\":\"岁月的童话\",\"subtype\":\"movie\",\"year\":\"1991\",\"rate_average\":8.5,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/2cac9183be7c671e7938d14d10a7f9db.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"16457\",\"title\":\"龙猫\",\"subtype\":\"movie\",\"year\":\"1988\",\"rate_average\":9.1,\"score\":90,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/fd13c7965bdeeba815d148299400766f.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1611\",\"title\":\"机动战士高达：逆袭的夏亚\",\"subtype\":\"movie\",\"year\":\"1988\",\"rate_average\":8.9,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/7f8484903f8d9450d9ff354125770cf8.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"1323\",\"title\":\"阿基拉\",\"subtype\":\"movie\",\"year\":\"1988\",\"rate_average\":8.3,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/d1ef90b6b0adddd3c0b349783b8ab413.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"938\",\"title\":\"天空之城\",\"subtype\":\"movie\",\"year\":\"1986\",\"rate_average\":9,\"score\":90,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/f06141428be64d426ceb75c21e8a81af.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"},{\"id\":\"940\",\"title\":\"风之谷\",\"subtype\":\"movie\",\"year\":\"1984\",\"rate_average\":8.8,\"score\":80,\"img_url\":\"http:\\/\\/img.dongman.fm\\/subject\\/2cef317ba759688266b199321a14b405.jpg\",\"is_end\":\"1\",\"play_count_str\":\"全1集\"}]";


    @Override
    public void onAttach(Activity activity) {
        mContext = activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.homepage_fragment_focus, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        mRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_widget);
        mSearchView = root.findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        //TODO Banner
        ArrayList<View> viewList = new ArrayList<>();
        BannerView iv = new BannerView(mContext);
        iv.setImage("http://img.dongman.fm/public/b94a110dbe68193c8cc067435c702224.jpg");
        iv.setDescription("暗黑系来袭");
        viewList.add(iv);

        iv = new BannerView(mContext);
        iv.setImage("http://img.dongman.fm/public/ef6acfa198df5ce68e64d5817f621d8f.jpg");
        iv.setDescription("和平的使者");
        viewList.add(iv);

        iv = new BannerView(mContext);
        iv.setImage("http://img.dongman.fm/public/f9d68537d0939848ed7ebd084f28fb98.jpg");
        iv.setDescription("青春美少女");
        viewList.add(iv);

        //TODO Recommend
        Banner banner = (Banner) root.findViewById(R.id.banner);
        SimpleAdapter adapter = new SimpleAdapter(viewList);
        banner.setAdapter(adapter);


        mAnimesRecycleView = (RecyclerView) root.findViewById(R.id.relative_anime_recycleview);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mAnimesRecycleView.setLayoutManager(mLinearLayoutManager);
        mAnimesAdapter = new RelativeAdapter(getActivity(), RelativeAdapter.ANIMES);
        mAnimesRecycleView.addItemDecoration(new SpacesItemDecoration(20, 0, 10, 10));
        mAnimesRecycleView.setAdapter(mAnimesAdapter);
        setTestData();

        // init 漫团
        initMantuan(root);

        // init 专题
        initTopic(root);

    }

    private void initMantuan(View root) {
        mMantuan1 = (ManTuanHomeItemView) root.findViewById(R.id.recommend_group_1);
        mMantuan1.setImage(getActivity().getResources().getDrawable(R.drawable.test_mantuan1));
        mMantuan1.setTitle("后宫");
        mMantuan1.setOnClickListener(this);
        mMantuan2 = (ManTuanHomeItemView) root.findViewById(R.id.recommend_group_2);
        mMantuan2.setImage(getActivity().getResources().getDrawable(R.drawable.test_mantuan2));
        mMantuan2.setTitle("异世界");
        mMantuan2.setOnClickListener(this);
        mMantuan3 = (ManTuanHomeItemView) root.findViewById(R.id.recommend_group_3);
        mMantuan3.setImage(getActivity().getResources().getDrawable(R.drawable.test_mantuan3));
        mMantuan3.setTitle("动漫推荐");
        mMantuan3.setOnClickListener(this);
        mMantuan4 = (ManTuanHomeItemView) root.findViewById(R.id.recommend_group_4);
        mMantuan4.setImage(getActivity().getResources().getDrawable(R.drawable.test_mantuan4));
        mMantuan4.setTitle("一拳超人");
        mMantuan4.setOnClickListener(this);
        mMantuan5 = (ManTuanHomeItemView) root.findViewById(R.id.recommend_group_5);
        mMantuan5.setImage(getActivity().getResources().getDrawable(R.drawable.test_mantuan5));
        mMantuan5.setTitle("禁忌咒纹");
        mMantuan5.setOnClickListener(this);
        mMantuan6 = (ManTuanHomeItemView) root.findViewById(R.id.recommend_group_6);
        mMantuan6.setImage(getActivity().getResources().getDrawable(R.drawable.test_mantuan6));
        mMantuan6.setTitle("番米的家");
        mMantuan6.setOnClickListener(this);
    }

    private void initTopic(View root) {
        mTopic1 = (TopicHomeItemView) root.findViewById(R.id.topic_item_1);
        mTopic1.setImage("http://img.dongman.fm/public/5fd7b6b1daeeecd9882ccf4f62145190.jpg");
        mTopic1.setTitle("未来科技动漫大盘点！科技玩人类！");
        mTopic1.setOnClickListener(this);
        mTopic2 = (TopicHomeItemView) root.findViewById(R.id.topic_item_2);
        mTopic2.setImage("http://img.dongman.fm/public/f200d71751f3744ab559bf272daecd24.jpg");
        mTopic2.setTitle("1989-2015日本历年TV霸权动漫盘点");
        mTopic2.setOnClickListener(this);
        mTopic3 = (TopicHomeItemView) root.findViewById(R.id.topic_item_3);
        mTopic3.setImage("http://img.dongman.fm/public/11dbe9a8131a87a25d62f962cc469c38.jpg");
        mTopic3.setTitle("画风如此怪异！请尽情蹂躏我的眼睛吧！！");
        mTopic3.setOnClickListener(this);
    }

    private void setTestData() {
        try {
            JSONArray relativeSubjects = new JSONArray(testAnimesData);
            if (relativeSubjects != null && relativeSubjects.length() > 0) {
                ArrayList mAnimes = new ArrayList<>(relativeSubjects.length());
                for (int i = 0; i < relativeSubjects.length(); i++) {
                    JSONObject object = relativeSubjects.getJSONObject(i);
                    RelativeRecommend animeInfo = RelativeRecommend.create(object);
                    mAnimes.add(animeInfo);
                }
                mAnimesAdapter.setData(mAnimes);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topic_item_1:
            case R.id.topic_item_2:
            case R.id.topic_item_3:
                Intent subjectDetail = new Intent();
                subjectDetail.setAction("com.dongman.fm.subject");
                subjectDetail.putExtra("id", "154");
                mContext.startActivity(subjectDetail);
                break;
            case R.id.recommend_group_1:
            case R.id.recommend_group_2:
            case R.id.recommend_group_3:
            case R.id.recommend_group_4:
            case R.id.recommend_group_5:
            case R.id.recommend_group_6:
                Intent groupDetail = new Intent(mContext, GroupDetailActivity.class);
                mContext.startActivity(groupDetail);
                break;
            case R.id.search_view:
                Intent search = new Intent(mContext, SearchActivity.class);
                mContext.startActivity(search);
                break;
            default:
                break;
        }
    }

    class SimpleAdapter extends PagerAdapter {

        private ArrayList<View> mViewList = new ArrayList<View>();

        public SimpleAdapter(ArrayList<View> list) {
            mViewList = list;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mViewList.get(position);
            container.removeView(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
