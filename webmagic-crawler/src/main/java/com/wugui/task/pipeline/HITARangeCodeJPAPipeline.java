package com.wugui.task.pipeline;

import com.wugui.dao.RangeCodeContentDao;
import com.wugui.pojo.RangeCodeContent;
import com.wugui.task.processor.HITARangeCodeProcessor;
import com.wugui.utils.SpringUtil;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
* HITA 数据集入库
*
* @author: huzekang
* @Date: 2019-06-01
*/
@Component
public class HITARangeCodeJPAPipeline implements Pipeline {

	
	@Override
	public void process(ResultItems resultItems, Task task) {
		RangeCodeContentDao rangeCodeContentDao = SpringUtil.getBean(RangeCodeContentDao.class);

		resultItems.getAll().forEach((k,v)->{
			if (k.equals(HITARangeCodeProcessor.RANGE_CODE_CONTENT)) {
				rangeCodeContentDao.save((RangeCodeContent) v);
			}
		});


	}

}
