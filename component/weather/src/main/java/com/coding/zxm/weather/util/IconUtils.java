package com.coding.zxm.weather.util;


import com.coding.zxm.weather.R;

public class IconUtils {

    public static int getWeatherIcon(String code) {
        int iconId = -1;
        switch (code) {
            case "100":
                iconId = R.drawable.ic_100_fill;
                break;
            case "101":
                iconId = R.drawable.ic_101_fill;
                break;
            case "102":
                iconId = R.drawable.ic_102_fill;
                break;
            case "103":
                iconId = R.drawable.ic_103_fill;
                break;
            case "104":
                iconId = R.drawable.ic_104_fill;
                break;
            case "150":
                iconId = R.drawable.ic_150_fill;
                break;
            case "151":
                iconId = R.drawable.ic_151_fill;
                break;
            case "152":
                iconId = R.drawable.ic_152_fill;
                break;
            case "153":
                iconId = R.drawable.ic_153_fill;
                break;
            case "300":
                iconId = R.drawable.ic_300_fill;
                break;
            case "301":
                iconId = R.drawable.ic_301_fill;
                break;
            case "302":
                iconId = R.drawable.ic_302_fill;
                break;
            case "303":
                iconId = R.drawable.ic_303_fill;
                break;
            case "304":
                iconId = R.drawable.ic_304_fill;
                break;
            case "305":
                iconId = R.drawable.ic_305_fill;
                break;
            case "306":
                iconId = R.drawable.ic_306_fill;
                break;
            case "307":
                iconId = R.drawable.ic_307_fill;
                break;
            case "308":
                iconId = R.drawable.ic_308_fill;
                break;
            case "309":
                iconId = R.drawable.ic_309_fill;
                break;
            case "310":
                iconId = R.drawable.ic_310_fill;
                break;
            case "311":
                iconId = R.drawable.ic_311_fill;
                break;
            case "312":
                iconId = R.drawable.ic_312_fill;
                break;
            case "313":
                iconId = R.drawable.ic_313_fill;
                break;
            case "314":
                iconId = R.drawable.ic_314_fill;
                break;
            case "315":
                iconId = R.drawable.ic_315_fill;
                break;
            case "316":
                iconId = R.drawable.ic_316_fill;
                break;
            case "317":
                iconId = R.drawable.ic_317_fill;
                break;
            case "318":
                iconId = R.drawable.ic_318_fill;
                break;
            case "350":
                iconId = R.drawable.ic_350_fill;
                break;
            case "351":
                iconId = R.drawable.ic_351_fill;
                break;
            case "399":
                iconId = R.drawable.ic_399_fill;
                break;
            case "400":
                iconId = R.drawable.ic_400_fill;
                break;
            case "401":
                iconId = R.drawable.ic_401_fill;
                break;
            case "402":
                iconId = R.drawable.ic_402_fill;
                break;
            case "403":
                iconId = R.drawable.ic_403_fill;
                break;
            case "404":
                iconId = R.drawable.ic_404_fill;
                break;
            case "405":
                iconId = R.drawable.ic_405_fill;
                break;
            case "406":
                iconId = R.drawable.ic_406_fill;
                break;
            case "407":
                iconId = R.drawable.ic_407_fill;
                break;
            case "408":
                iconId = R.drawable.ic_408_fill;
                break;
            case "409":
                iconId = R.drawable.ic_409_fill;
                break;
            case "410":
                iconId = R.drawable.ic_410_fill;
                break;
            case "499":
                iconId = R.drawable.ic_499_fill;
                break;
            case "456":
                iconId = R.drawable.ic_456_fill;
                break;
            case "457":
                iconId = R.drawable.ic_457_fill;
                break;
            case "500":
                iconId = R.drawable.ic_500_fill;
                break;
            case "501":
                iconId = R.drawable.ic_501_fill;
                break;
            case "502":
                iconId = R.drawable.ic_502_fill;
                break;
            case "503":
                iconId = R.drawable.ic_503_fill;
                break;
            case "504":
                iconId = R.drawable.ic_504_fill;
                break;
            case "507":
                iconId = R.drawable.ic_507_fill;
                break;
            case "508":
                iconId = R.drawable.ic_508_fill;
                break;
            case "509":
                iconId = R.drawable.ic_509_fill;
                break;
            case "510":
                iconId = R.drawable.ic_510_fill;
                break;
            case "511":
                iconId = R.drawable.ic_511_fill;
                break;
            case "512":
                iconId = R.drawable.ic_512_fill;
                break;
            case "513":
                iconId = R.drawable.ic_513_fill;
                break;
            case "514":
                iconId = R.drawable.ic_514_fill;
                break;
            case "515":
                iconId = R.drawable.ic_515_fill;
                break;
            case "900":
                iconId = R.drawable.ic_900_fill;
                break;
            case "901":
                iconId = R.drawable.ic_901_fill;
                break;
            case "999":
                iconId = R.drawable.ic_999_fill;
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
