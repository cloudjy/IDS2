/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.gnamp.terminal.config;

import com.gnamp.server.utils.DomUtils;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.*;

// Referenced classes of package com.gnamp.terminal.config:
//            TimeBoundInt, TimeBound, DailyPowerConfig, PowerPoint, 
//            WeeklyPowerConfig, PowerConfig

public class LocalConfig
{
    private static interface ValueScope
    {

        public abstract boolean valid(Object obj);
    }


    public LocalConfig()
    {
        mBrightness = null;
        mVolume = null;
        mStandby = null;
        mDownload = null;
        mDemand = null;
        mPower = null;
    }

    public static int parseHHmm(String text)
    {
        if(text == null)
            return -1;
        if(!text.matches("^(([0-1][0-9])|(2[0-3]))\\:[0-5][0-9]$"))
            return -1;
        try
        {
            String arrays[] = text.split("\\:");
            int hour = Integer.parseInt(arrays[0].trim());
            int min = Integer.parseInt(arrays[1].trim());
            return hour * 3600 + min * 60;
        }
        catch(Exception e)
        {
            return -1;
        }
    }

    public static List pareTimeBoundInts(String text)
    {
        return pareTimeBoundInts(text, mScope0to10);
    }

    public static List pareTimeBoundInts(String text, ValueScope scope)
    {
        List timeBoundInts = new ArrayList();
        if(StringUtils.isBlank(text))
            return timeBoundInts;
        text = text.replace(" ", "");
        text = text.replace("\t", "");
        text = text.replace("][", "|");
        text = text.replace("];[", "|");
        text = text.replace("[", "");
        text = text.replace("]", "");
        text = text.replace("-", ",");
        String all[] = text.split("\\|");
        if(all != null)
        {
            String as[];
            int j = (as = all).length;
            for(int i = 0; i < j; i++)
            {
                String rows = as[i];
                String cols[] = rows.split("\\,");
                try
                {
                    int start = parseHHmm(cols[0]);
                    int stop = parseHHmm(cols[1]);
                    int val = Integer.parseInt(cols[2]);
                    if(start >= 0 && stop > start && (scope == null || scope.valid(Integer.valueOf(val))))
                    {
                        TimeBoundInt bound = new TimeBoundInt();
                        bound.mStart = start;
                        bound.mStop = stop;
                        bound.mValue = val;
                        timeBoundInts.add(bound);
                    }
                }
                catch(Exception exception) { }
            }

        }
        Collections.sort(timeBoundInts, TimeBoundInt.mComparator);
        return timeBoundInts;
    }

    public static String timeBoundIntToText(List timeBoundInts)
    {
        String text = "";
        if(timeBoundInts != null)
        {
            for(Iterator iterator = timeBoundInts.iterator(); iterator.hasNext();)
            {
                TimeBoundInt timeBoundInt = (TimeBoundInt)iterator.next();
                if(timeBoundInt.mStart >= 0 && timeBoundInt.mStop < 86400 && timeBoundInt.mStart < timeBoundInt.mStop)
                    text = (new StringBuilder(String.valueOf(text))).append(String.format("[%02d:%02d,%02d:%02d,%d]", new Object[] {
                        Integer.valueOf(timeBoundInt.mStart / 3600), Integer.valueOf((timeBoundInt.mStart % 3600) / 60), Integer.valueOf(timeBoundInt.mStop / 3600), Integer.valueOf((timeBoundInt.mStop % 3600) / 60), Integer.valueOf(timeBoundInt.mValue)
                    })).toString();
            }

        }
        return text;
    }

    public static List pareTimeBounds(String text)
    {
        List timeBounds = new ArrayList();
        if(StringUtils.isBlank(text))
            return timeBounds;
        text = text.replace(" ", "");
        text = text.replace("\t", "");
        text = text.replace("][", "|");
        text = text.replace("];[", "|");
        text = text.replace("[", "");
        text = text.replace("]", "");
        text = text.replace("-", ",");
        String all[] = text.split("\\|");
        if(all != null)
        {
            String as[];
            int j = (as = all).length;
            for(int i = 0; i < j; i++)
            {
                String rows = as[i];
                String cols[] = rows.split("\\,");
                try
                {
                    int start = parseHHmm(cols[0]);
                    int stop = parseHHmm(cols[1]);
                    if(start >= 0 && stop > start)
                    {
                        TimeBound bound = new TimeBound();
                        bound.mStart = start;
                        bound.mStop = stop;
                        timeBounds.add(bound);
                    }
                }
                catch(Exception exception) { }
            }

        }
        Collections.sort(timeBounds, TimeBound.mComparator);
        return timeBounds;
    }

    public static String timeBoundToText(List timeBounds)
    {
        String text = "";
        if(timeBounds != null)
        {
            for(Iterator iterator = timeBounds.iterator(); iterator.hasNext();)
            {
                TimeBound timeBound = (TimeBound)iterator.next();
                if(timeBound.mStart >= 0 && timeBound.mStop < 86400 && timeBound.mStart < timeBound.mStop)
                    text = (new StringBuilder(String.valueOf(text))).append(String.format("[%02d:%02d,%02d:%02d]", new Object[] {
                        Integer.valueOf(timeBound.mStart / 3600), Integer.valueOf((timeBound.mStart % 3600) / 60), Integer.valueOf(timeBound.mStop / 3600), Integer.valueOf((timeBound.mStop % 3600) / 60)
                    })).toString();
            }

        }
        return text;
    }

    public static DailyPowerConfig parseDailyPower(String text)
    {
        if(StringUtils.isBlank(text))
            return null;
        text = text.replace(" ", "");
        text = text.replace("\t", "");
        text = text.replace("[", "");
        text = text.replace("]", "");
        String cols[] = text.split("\\,");
        if(cols == null || cols.length != 2)
            return null;
        else
            return parseDailyPower(cols[0], cols[1]);
    }

    public static DailyPowerConfig parseDailyPower(String pon, String poff)
    {
        if(StringUtils.isBlank(pon) || StringUtils.isBlank(poff))
            return null;
        int start = parseHHmm(pon);
        int stop = parseHHmm(poff);
        if(start >= 0 && start <= 86400 && stop >= 0 && stop <= 86400)
        {
            DailyPowerConfig dailyPowerConfig = new DailyPowerConfig();
            PowerPoint point = new PowerPoint((start % 86400) / 3600, (start % 3600) / 60, start % 60, (stop % 86400) / 3600, (stop % 3600) / 60, stop % 60);
            dailyPowerConfig.setPowerPoint(point);
            return dailyPowerConfig;
        } else
        {
            return null;
        }
    }

    public static WeeklyPowerConfig parseWeeklyPower(String modeweekly)
    {
        if(StringUtils.isBlank(modeweekly))
            return null;
        Pattern pattern = Pattern.compile("\\[\\s*([0-6])\\s*[,|;]\\s*(\\d|[0-2]\\d)\\s*:\\s*(\\d|[0-5]\\d)\\s*[,|;|-]\\s*(\\d|[0-2]\\d)\\s*:\\s*(\\d|[0-5]\\d)\\s*\\]");
        Matcher matcher = pattern.matcher(modeweekly);
        WeeklyPowerConfig wConfig = null;
        while(matcher.find()) 
        {
            int day = Integer.parseInt(matcher.group(1));
            int starthour = Integer.parseInt(matcher.group(2));
            int startmin = Integer.parseInt(matcher.group(3));
            int endhour = Integer.parseInt(matcher.group(4));
            int endmin = Integer.parseInt(matcher.group(5));
            if(starthour <= 23 && starthour >= 0 && endhour <= 23 && endhour >= 0 && startmin <= 59 && startmin >= 0 && endmin <= 59 && endmin >= 0 && day <= 6 && day >= 0)
            {
                if(wConfig == null)
                    wConfig = new WeeklyPowerConfig();
                PowerPoint point = new PowerPoint(starthour, startmin, 0, endhour, endmin, 0);
                wConfig.setPowerPoint(day, point);
            }
        }
        return wConfig;
    }

    public static String powerToText(PowerConfig power)
    {
        String text = "";
        if(power != null && (power instanceof DailyPowerConfig))
        {
            PowerPoint point = ((DailyPowerConfig)power).mPoint;
            if(point != null && point.valid())
                text = String.format("[%02d:%02d,%02d:%02d]", new Object[] {
                    Integer.valueOf(point.wPonHour), Integer.valueOf(point.wPonMinute), Integer.valueOf(point.wPoffHour), Integer.valueOf(point.wPoffMinute)
                });
        } else
        if(power != null && (power instanceof WeeklyPowerConfig))
        {
            WeeklyPowerConfig weekly = (WeeklyPowerConfig)power;
            PowerPoint points[] = weekly.mPoints;
            if(points != null && points.length == 7)
            {
                for(int i = 0; i < points.length; i++)
                    if(WeeklyPowerConfig.validPowerPoint(points[i]))
                        text = (new StringBuilder(String.valueOf(text))).append(String.format("[%d,%02d:%02d,%02d:%02d]", new Object[] {
                            Integer.valueOf(i), Integer.valueOf(points[i].wPonHour), Integer.valueOf(points[i].wPonMinute), Integer.valueOf(points[i].wPoffHour), Integer.valueOf(points[i].wPoffMinute)
                        })).toString();

            }
        }
        return text;
    }

    public static void fillToDOM(Element elemRoot, LocalConfig config)
    {
        if(elemRoot != null && config != null)
            config._fillToDOM(elemRoot);
    }

    private void _fillToDOM(Element elemRoot)
    {
        NodeList nodeList = elemRoot.getElementsByTagName("local_config");
        int nodeNum = nodeList == null ? 0 : nodeList.getLength();
        for(int i = 0; i < nodeNum; i++)
            elemRoot.removeChild(nodeList.item(i));

        Element elemLocalRoot = (Element)elemRoot.appendChild(elemRoot.getOwnerDocument().createElement("local_config"));
        if(elemLocalRoot == null)
            return;
        if(mPower != null && (mPower instanceof DailyPowerConfig))
        {
            PowerPoint point = ((DailyPowerConfig)mPower).mPoint;
            if(point != null && point.valid())
            {
                DomUtils.putStringContent(elemLocalRoot, "pontime", String.format("%02d:%02d", new Object[] {
                    Integer.valueOf(point.wPonHour), Integer.valueOf(point.wPonMinute)
                }));
                DomUtils.putStringContent(elemLocalRoot, "pofftime", String.format("%02d:%02d", new Object[] {
                    Integer.valueOf(point.wPoffHour), Integer.valueOf(point.wPoffMinute)
                }));
            }
        } else
        if(mPower != null && (mPower instanceof WeeklyPowerConfig))
        {
            String text = "";
            WeeklyPowerConfig weekly = (WeeklyPowerConfig)mPower;
            PowerPoint points[] = weekly.mPoints;
            if(points != null && points.length == 7)
            {
                for(int i = 0; i < points.length; i++)
                    if(WeeklyPowerConfig.validPowerPoint(points[i]))
                        text = (new StringBuilder(String.valueOf(text))).append(String.format("[%d,%02d:%02d,%02d:%02d]", new Object[] {
                            Integer.valueOf(i), Integer.valueOf(points[i].wPonHour), Integer.valueOf(points[i].wPonMinute), Integer.valueOf(points[i].wPoffHour), Integer.valueOf(points[i].wPoffMinute)
                        })).toString();

            }
            if(text.length() > 0)
                DomUtils.putStringContent(elemLocalRoot, "power", text);
        }
        if(mStandby != null)
            DomUtils.putStringContent(elemLocalRoot, "standby", timeBoundToText(mStandby));
        if(mDownload != null)
            DomUtils.putStringContent(elemLocalRoot, "download", timeBoundToText(mDownload));
        if(mDemand != null)
            DomUtils.putStringContent(elemLocalRoot, "demand", timeBoundToText(mDemand));
        if(mBrightness != null && mBrightness.size() > 0)
            DomUtils.putStringContent(elemLocalRoot, "bright", timeBoundIntToText(mBrightness));
        if(mVolume != null && mVolume.size() > 0)
            DomUtils.putStringContent(elemLocalRoot, "volume", timeBoundIntToText(mVolume));
    }

    public static LocalConfig parseFromDOM(Element elemRoot)
    {
        if(elemRoot == null)
            return null;
        Element elemLocalRoot = (Element)DomUtils.selectSingleNode(elemRoot, "local_config");
        if(elemLocalRoot == null)
        {
            return null;
        } else
        {
            LocalConfig localConfig = new LocalConfig();
            localConfig._parseFromDOM(elemRoot);
            return localConfig;
        }
    }

    private void _parseFromDOM(Element elemRoot)
    {
        mPower = null;
        mStandby = null;
        mDownload = null;
        mDemand = null;
        mBrightness = null;
        mVolume = null;
        Element elemLocalRoot = (Element)DomUtils.selectSingleNode(elemRoot, "local_config");
        if(elemLocalRoot == null)
            return;
        if(DomUtils.selectSingleNode(elemLocalRoot, "pontime") != null && DomUtils.selectSingleNode(elemLocalRoot, "pofftime") != null)
            mPower = parseDailyPower(DomUtils.getStringContent(elemLocalRoot, "pontime", ""), DomUtils.getStringContent(elemLocalRoot, "pofftime", ""));
        else
        if(DomUtils.selectSingleNode(elemLocalRoot, "power") != null)
            mPower = parseWeeklyPower(DomUtils.getStringContent(elemLocalRoot, "power", ""));
        if(DomUtils.selectSingleNode(elemLocalRoot, "standby") != null)
            mStandby = pareTimeBounds(DomUtils.getStringContent(elemLocalRoot, "standby", ""));
        if(DomUtils.selectSingleNode(elemLocalRoot, "download") != null)
            mDownload = pareTimeBounds(DomUtils.getStringContent(elemLocalRoot, "download", ""));
        if(DomUtils.selectSingleNode(elemLocalRoot, "demand") != null)
            mDemand = pareTimeBounds(DomUtils.getStringContent(elemLocalRoot, "demand", ""));
        if(DomUtils.selectSingleNode(elemLocalRoot, "bright") != null)
            mBrightness = pareTimeBoundInts(DomUtils.getStringContent(elemLocalRoot, "bright", ""));
        if(DomUtils.selectSingleNode(elemLocalRoot, "volume") != null)
            mVolume = pareTimeBoundInts(DomUtils.getStringContent(elemLocalRoot, "volume", ""));
    }

    private static final ValueScope mScope0to10 = new ValueScope() {

        public boolean valid(Integer value)
        {
            return value.intValue() >= 0 && value.intValue() <= 10;
        }

        public  boolean valid(Object obj)
        {
            return valid((Integer)obj);
        }

    };
    public List mBrightness;
    public List mVolume;
    public List mStandby;
    public List mDownload;
    public List mDemand;
    public PowerConfig mPower;
    public static final String NODE_LOCAL_ROOT = "local_config";
    public static final String NODE_STANDBY = "standby";
    public static final String NODE_DOWNLOAD = "download";
    public static final String NODE_DEMAND = "demand";
    public static final String NODE_BRIGHT = "bright";
    public static final String NODE_VOLUME = "volume";
    public static final String NODE_PONTIME = "pontime";
    public static final String NODE_POFFTIME = "pofftime";
    public static final String NODE_WEEKLY_POWER = "power";

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\Users\Administrator\Desktop\gnamp-struts.jar
	Total time: 24 ms
	Jad reported messages/errors:
Couldn't resolve all exception handlers in method parseHHmm
	Exit status: 0
	Caught exceptions:
*/