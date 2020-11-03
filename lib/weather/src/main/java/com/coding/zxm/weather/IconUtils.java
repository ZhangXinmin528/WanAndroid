package com.coding.zxm.weather;


public class IconUtils {

    public static int getWeatherIcon(String code) {
        int iconId = -1;
        switch (code) {
            case "100":
                iconId = R.mipmap.icon100;
                break;
            case "101":
                iconId = R.mipmap.icon101;
                break;
            case "102":
                iconId = R.mipmap.icon102;
                break;
            case "103":
                iconId = R.mipmap.icon103;
                break;
            case "104":
                iconId = R.mipmap.icon104;
                break;
            case "150":
                iconId = R.mipmap.icon150;
                break;
            case "153":
                iconId = R.mipmap.icon153;
                break;
            case "154":
                iconId = R.mipmap.icon154;
                break;
            case "300":
                iconId = R.mipmap.icon300;
                break;
            case "301":
                iconId = R.mipmap.icon301;
                break;
            case "302":
                iconId = R.mipmap.icon302;
                break;
            case "303":
                iconId = R.mipmap.icon303;
                break;
            case "304":
                iconId = R.mipmap.icon304;
                break;
            case "305":
                iconId = R.mipmap.icon305;
                break;
            case "306":
                iconId = R.mipmap.icon306;
                break;
            case "307":
                iconId = R.mipmap.icon307;
                break;
            case "308":
                iconId = R.mipmap.icon308;
                break;
            case "309":
                iconId = R.mipmap.icon309;
                break;
            case "310":
                iconId = R.mipmap.icon310;
                break;
            case "311":
                iconId = R.mipmap.icon311;
                break;
            case "312":
                iconId = R.mipmap.icon312;
                break;
            case "313":
                iconId = R.mipmap.icon313;
                break;
            case "314":
                iconId = R.mipmap.icon314;
                break;
            case "315":
                iconId = R.mipmap.icon315;
                break;
            case "316":
                iconId = R.mipmap.icon316;
                break;
            case "317":
                iconId = R.mipmap.icon317;
                break;
            case "318":
                iconId = R.mipmap.icon318;
                break;
            case "350":
                iconId = R.mipmap.icon350;
                break;
            case "351":
                iconId = R.mipmap.icon351;
                break;
            case "399":
                iconId = R.mipmap.icon399;
                break;
            case "400":
                iconId = R.mipmap.icon400;
                break;
            case "401":
                iconId = R.mipmap.icon401;
                break;
            case "402":
                iconId = R.mipmap.icon402;
                break;
            case "403":
                iconId = R.mipmap.icon403;
                break;
            case "404":
                iconId = R.mipmap.icon404;
                break;
            case "405":
                iconId = R.mipmap.icon405;
                break;
            case "406":
                iconId = R.mipmap.icon406;
                break;
            case "407":
                iconId = R.mipmap.icon407;
                break;
            case "408":
                iconId = R.mipmap.icon408;
                break;
            case "409":
                iconId = R.mipmap.icon409;
                break;
            case "410":
                iconId = R.mipmap.icon410;
                break;
            case "499":
                iconId = R.mipmap.icon499;
                break;
            case "456":
                iconId = R.mipmap.icon456;
                break;
            case "457":
                iconId = R.mipmap.icon457;
                break;
            case "500":
                iconId = R.mipmap.icon500;
                break;
            case "501":
                iconId = R.mipmap.icon501;
                break;
            case "502":
                iconId = R.mipmap.icon502;
                break;
            case "503":
                iconId = R.mipmap.icon503;
                break;
            case "504":
                iconId = R.mipmap.icon504;
                break;
            case "507":
                iconId = R.mipmap.icon507;
                break;
            case "508":
                iconId = R.mipmap.icon508;
                break;
            case "509":
                iconId = R.mipmap.icon509;
                break;
            case "510":
                iconId = R.mipmap.icon510;
                break;
            case "511":
                iconId = R.mipmap.icon511;
                break;
            case "512":
                iconId = R.mipmap.icon512;
                break;
            case "513":
                iconId = R.mipmap.icon513;
                break;
            case "514":
                iconId = R.mipmap.icon514;
                break;
            case "515":
                iconId = R.mipmap.icon515;
                break;
            case "900":
                iconId = R.mipmap.icon900;
                break;
            case "901":
                iconId = R.mipmap.icon901;
                break;
            case "999":
                iconId = R.mipmap.icon999;
                break;

        }
        return iconId;
    }

    /**
     * 获取白天背景
     */
    public static int getDayBack(String weather) {
        int imageId;
        switch (weather) {
            case "100":
                imageId = R.mipmap.back_100d;
                break;
            case "101":
                imageId = R.mipmap.back_101d;
                break;
            case "102":
                imageId = R.mipmap.back_102d;
                break;
            case "103":
                imageId = R.mipmap.back_103d;
                break;
            case "104":
                imageId = R.mipmap.back_104d;
                break;
            case "200":
                imageId = R.mipmap.back_200d;
                break;
            case "201":
                imageId = R.mipmap.back_210d;
                break;
            case "202":
                imageId = R.mipmap.back_202d;
                break;
            case "203":
                imageId = R.mipmap.back_203d;
                break;
            case "204":
                imageId = R.mipmap.back_204d;
                break;
            case "205":
                imageId = R.mipmap.back_205d;
                break;
            case "206":
                imageId = R.mipmap.back_206d;
                break;
            case "207":
                imageId = R.mipmap.back_207d;
                break;
            case "208":
                imageId = R.mipmap.back_208d;
                break;
            case "209":
                imageId = R.mipmap.back_209d;
                break;
            case "210":
                imageId = R.mipmap.back_210d;
                break;
            case "211":
                imageId = R.mipmap.back_211d;
                break;
            case "212":
                imageId = R.mipmap.back_212d;
                break;
            case "213":
                imageId = R.mipmap.back_213d;
                break;
            case "300":
                imageId = R.mipmap.back_300d;
                break;
            case "301":
                imageId = R.mipmap.back_301d;
                break;
            case "302":
                imageId = R.mipmap.back_302d;
                break;
            case "303":
                imageId = R.mipmap.back_303d;
                break;
            case "304":
                imageId = R.mipmap.back_304d;
                break;
            case "305":
                imageId = R.mipmap.back_305d;
                break;
            case "306":
                imageId = R.mipmap.back_306d;
                break;
            case "307":
                imageId = R.mipmap.back_307d;
                break;
            case "308":
                imageId = R.mipmap.back_308d;
                break;
            case "309":
                imageId = R.mipmap.back_309d;
                break;
            case "310":
                imageId = R.mipmap.back_310d;
                break;
            case "311":
                imageId = R.mipmap.back_311d;
                break;
            case "312":
                imageId = R.mipmap.back_312d;
                break;
            case "313":
                imageId = R.mipmap.back_313d;
                break;
            case "314":
                imageId = R.mipmap.back_314d;
                break;
            case "315":
                imageId = R.mipmap.back_315d;
                break;
            case "316":
                imageId = R.mipmap.back_316d;
                break;
            case "317":
                imageId = R.mipmap.back_317d;
                break;
            case "318":
                imageId = R.mipmap.back_318d;
                break;
            case "399":
                imageId = R.mipmap.back_399d;
                break;
            case "400":
                imageId = R.mipmap.back_400d;
                break;
            case "401":
                imageId = R.mipmap.back_401d;
                break;
            case "402":
                imageId = R.mipmap.back_402d;
                break;
            case "403":
                imageId = R.mipmap.back_403d;
                break;
            case "404":
                imageId = R.mipmap.back_404d;
                break;
            case "405":
                imageId = R.mipmap.back_405d;
                break;
            case "406":
                imageId = R.mipmap.back_406d;
                break;
            case "407":
                imageId = R.mipmap.back_407d;
                break;
            case "408":
                imageId = R.mipmap.back_408d;
                break;
            case "409":
                imageId = R.mipmap.back_409d;
                break;
            case "410":
                imageId = R.mipmap.back_410d;
                break;
            case "499":
                imageId = R.mipmap.back_499d;
                break;
            case "500":
                imageId = R.mipmap.back_500d;
                break;
            case "501":
                imageId = R.mipmap.back_501d;
                break;
            case "502":
                imageId = R.mipmap.back_502d;
                break;
            case "503":
                imageId = R.mipmap.back_503d;
                break;
            case "504":
                imageId = R.mipmap.back_504d;
                break;
            case "507":
                imageId = R.mipmap.back_507d;
                break;
            case "508":
                imageId = R.mipmap.back_508d;
                break;
            case "509":
                imageId = R.mipmap.back_509d;
                break;
            case "510":
                imageId = R.mipmap.back_510d;
                break;
            case "511":
                imageId = R.mipmap.back_511d;
                break;
            case "512":
                imageId = R.mipmap.back_512d;
                break;
            case "513":
                imageId = R.mipmap.back_513d;
                break;
            case "514":
                imageId = R.mipmap.back_514d;
                break;
            case "515":
                imageId = R.mipmap.back_515d;
                break;
            case "900":
                imageId = R.mipmap.back_900d;
                break;
            case "901":
                imageId = R.mipmap.back_901d;
                break;
            case "999":
                imageId = R.mipmap.back_999d;
                break;
            default:
                imageId = R.mipmap.back_100d;
                break;

        }
        return imageId;
    }

    /**
     * 获取晚上背景
     */
    public static int getNightBack(String weather) {
        int imageId;
        switch (weather) {
            case "100":
                imageId = R.mipmap.back_100n;
                break;
            case "101":
                imageId = R.mipmap.back_101n;
                break;
            case "102":
                imageId = R.mipmap.back_102n;
                break;
            case "103":
                imageId = R.mipmap.back_103n;
                break;
            case "104":
                imageId = R.mipmap.back_104n;
                break;
            case "200":
                imageId = R.mipmap.back_200n;
                break;
            case "201":
                imageId = R.mipmap.back_210n;
                break;
            case "202":
                imageId = R.mipmap.back_202n;
                break;
            case "203":
                imageId = R.mipmap.back_203n;
                break;
            case "204":
                imageId = R.mipmap.back_204n;
                break;
            case "205":
                imageId = R.mipmap.back_205n;
                break;
            case "206":
                imageId = R.mipmap.back_206n;
                break;
            case "207":
                imageId = R.mipmap.back_207n;
                break;
            case "208":
                imageId = R.mipmap.back_208n;
                break;
            case "209":
                imageId = R.mipmap.back_209n;
                break;
            case "210":
                imageId = R.mipmap.back_210n;
                break;
            case "211":
                imageId = R.mipmap.back_211n;
                break;
            case "212":
                imageId = R.mipmap.back_212n;
                break;
            case "213":
                imageId = R.mipmap.back_213n;
                break;
            case "300":
                imageId = R.mipmap.back_300n;
                break;
            case "301":
                imageId = R.mipmap.back_301n;
                break;
            case "302":
                imageId = R.mipmap.back_302n;
                break;
            case "303":
                imageId = R.mipmap.back_303n;
                break;
            case "304":
                imageId = R.mipmap.back_304n;
                break;
            case "305":
                imageId = R.mipmap.back_305n;
                break;
            case "306":
                imageId = R.mipmap.back_306n;
                break;
            case "307":
                imageId = R.mipmap.back_307n;
                break;
            case "308":
                imageId = R.mipmap.back_308n;
                break;
            case "309":
                imageId = R.mipmap.back_309n;
                break;
            case "310":
                imageId = R.mipmap.back_310n;
                break;
            case "311":
                imageId = R.mipmap.back_311n;
                break;
            case "312":
                imageId = R.mipmap.back_312n;
                break;
            case "313":
                imageId = R.mipmap.back_313n;
                break;
            case "314":
                imageId = R.mipmap.back_314n;
                break;
            case "315":
                imageId = R.mipmap.back_315n;
                break;
            case "316":
                imageId = R.mipmap.back_316n;
                break;
            case "317":
                imageId = R.mipmap.back_317n;
                break;
            case "318":
                imageId = R.mipmap.back_318n;
                break;
            case "399":
                imageId = R.mipmap.back_399n;
                break;
            case "400":
                imageId = R.mipmap.back_400n;
                break;
            case "401":
                imageId = R.mipmap.back_401n;
                break;
            case "402":
                imageId = R.mipmap.back_402n;
                break;
            case "403":
                imageId = R.mipmap.back_403n;
                break;
            case "404":
                imageId = R.mipmap.back_404n;
                break;
            case "405":
                imageId = R.mipmap.back_405n;
                break;
            case "406":
                imageId = R.mipmap.back_406n;
                break;
            case "407":
                imageId = R.mipmap.back_407n;
                break;
            case "408":
                imageId = R.mipmap.back_408n;
                break;
            case "409":
                imageId = R.mipmap.back_409n;
                break;
            case "410":
                imageId = R.mipmap.back_410n;
                break;
            case "499":
                imageId = R.mipmap.back_499n;
                break;
            case "500":
                imageId = R.mipmap.back_500n;
                break;
            case "501":
                imageId = R.mipmap.back_501n;
                break;
            case "502":
                imageId = R.mipmap.back_502n;
                break;
            case "503":
                imageId = R.mipmap.back_503n;
                break;
            case "504":
                imageId = R.mipmap.back_504n;
                break;
            case "507":
                imageId = R.mipmap.back_507n;
                break;
            case "508":
                imageId = R.mipmap.back_508n;
                break;
            case "509":
                imageId = R.mipmap.back_509n;
                break;
            case "510":
                imageId = R.mipmap.back_510n;
                break;
            case "511":
                imageId = R.mipmap.back_511n;
                break;
            case "512":
                imageId = R.mipmap.back_512n;
                break;
            case "513":
                imageId = R.mipmap.back_513n;
                break;
            case "514":
                imageId = R.mipmap.back_514n;
                break;
            case "515":
                imageId = R.mipmap.back_515n;
                break;
            case "900":
                imageId = R.mipmap.back_900n;
                break;
            case "901":
                imageId = R.mipmap.back_901n;
                break;
            case "999":
                imageId = R.mipmap.back_999n;
                break;
            default:
                imageId = R.mipmap.back_100n;
                break;

        }
        return imageId;
    }


}
