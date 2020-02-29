package com.zyao89.view.zloading;

import com.zyao89.view.zloading.ball.ElasticBallBuilder;
import com.zyao89.view.zloading.ball.InfectionBallBuilder;
import com.zyao89.view.zloading.ball.IntertwineBuilder;
import com.zyao89.view.zloading.circle.DoubleCircleBuilder;
import com.zyao89.view.zloading.circle.PacManBuilder;
import com.zyao89.view.zloading.circle.RotateCircleBuilder;
import com.zyao89.view.zloading.circle.SingleCircleBuilder;
import com.zyao89.view.zloading.circle.SnakeCircleBuilder;
import com.zyao89.view.zloading.clock.CircleBuilder;
import com.zyao89.view.zloading.clock.ClockBuilder;
import com.zyao89.view.zloading.path.MusicPathBuilder;
import com.zyao89.view.zloading.path.SearchPathBuilder;
import com.zyao89.view.zloading.path.StairsPathBuilder;
import com.zyao89.view.zloading.rect.ChartRectBuilder;
import com.zyao89.view.zloading.rect.StairsRectBuilder;
import com.zyao89.view.zloading.star.LeafBuilder;
import com.zyao89.view.zloading.star.StarBuilder;
import com.zyao89.view.zloading.text.TextBuilder;

public static enum Z_TYPE {
  CHART_RECT,
  CIRCLE(CircleBuilder.class),
  CIRCLE_CLOCK(ClockBuilder.class),
  DOUBLE_CIRCLE(ClockBuilder.class),
  ELASTIC_BALL(ClockBuilder.class),
  INFECTION_BALL(ClockBuilder.class),
  INTERTWINE(ClockBuilder.class),
  LEAF_ROTATE(ClockBuilder.class),
  MUSIC_PATH(ClockBuilder.class),
  PAC_MAN(ClockBuilder.class),
  ROTATE_CIRCLE(ClockBuilder.class),
  SEARCH_PATH(ClockBuilder.class),
  SINGLE_CIRCLE(ClockBuilder.class),
  SNAKE_CIRCLE(ClockBuilder.class),
  STAIRS_PATH(ClockBuilder.class),
  STAIRS_RECT(ClockBuilder.class),
  STAR_LOADING(StarBuilder.class),
  TEXT(StarBuilder.class);
  
  private final Class<?> mBuilderClass;
  
  static  {
    LEAF_ROTATE = new Z_TYPE("LEAF_ROTATE", 3, LeafBuilder.class);
    DOUBLE_CIRCLE = new Z_TYPE("DOUBLE_CIRCLE", 4, DoubleCircleBuilder.class);
    PAC_MAN = new Z_TYPE("PAC_MAN", 5, PacManBuilder.class);
    ELASTIC_BALL = new Z_TYPE("ELASTIC_BALL", 6, ElasticBallBuilder.class);
    INFECTION_BALL = new Z_TYPE("INFECTION_BALL", 7, InfectionBallBuilder.class);
    INTERTWINE = new Z_TYPE("INTERTWINE", 8, IntertwineBuilder.class);
    TEXT = new Z_TYPE("TEXT", 9, TextBuilder.class);
    SEARCH_PATH = new Z_TYPE("SEARCH_PATH", 10, SearchPathBuilder.class);
    ROTATE_CIRCLE = new Z_TYPE("ROTATE_CIRCLE", 11, RotateCircleBuilder.class);
    SINGLE_CIRCLE = new Z_TYPE("SINGLE_CIRCLE", 12, SingleCircleBuilder.class);
    SNAKE_CIRCLE = new Z_TYPE("SNAKE_CIRCLE", 13, SnakeCircleBuilder.class);
    STAIRS_PATH = new Z_TYPE("STAIRS_PATH", 14, StairsPathBuilder.class);
    MUSIC_PATH = new Z_TYPE("MUSIC_PATH", 15, MusicPathBuilder.class);
    STAIRS_RECT = new Z_TYPE("STAIRS_RECT", 16, StairsRectBuilder.class);
    Z_TYPE z_TYPE = new Z_TYPE("CHART_RECT", 17, ChartRectBuilder.class);
    CHART_RECT = z_TYPE;
    $VALUES = new Z_TYPE[] { 
        CIRCLE, CIRCLE_CLOCK, STAR_LOADING, LEAF_ROTATE, DOUBLE_CIRCLE, PAC_MAN, ELASTIC_BALL, INFECTION_BALL, INTERTWINE, TEXT, 
        SEARCH_PATH, ROTATE_CIRCLE, SINGLE_CIRCLE, SNAKE_CIRCLE, STAIRS_PATH, MUSIC_PATH, STAIRS_RECT, z_TYPE };
  }
  
  Z_TYPE(Class<?> paramClass) { this.mBuilderClass = paramClass; }
  
  <T extends ZLoadingBuilder> T newInstance() {
    try {
      return (T)this.mBuilderClass.newInstance();
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\Z_TYPE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */