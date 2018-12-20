package com.blog.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * 自定义返回JSON 数据格中日期格式化处理, 通过json传输,Java的Date类型的数据自动转成了时间戳
 * javabean对应属性的方法添加注解：@JsonSerialize(using =CustomDateSerializer.class)
 * 
 * @author Administrator
 * 
 */
public class CustomDateSerializer extends JsonSerializer<Date> {

	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
			throws JsonProcessingException, IOException {
		if (value != null) {
			String formattedDateString = datetimeFormat.format(value);
			jgen.writeString(formattedDateString);
		}
	}
}
