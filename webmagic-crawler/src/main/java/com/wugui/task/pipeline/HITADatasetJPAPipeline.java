package com.wugui.task.pipeline;

import com.wugui.dao.DatasetContentDao;
import com.wugui.dao.DatasetContentElementDao;
import com.wugui.dao.DatasetDao;
import com.wugui.dao.GithubUserDao;
import com.wugui.pojo.Dataset;
import com.wugui.pojo.DatasetContent;
import com.wugui.pojo.DatasetContentElement;
import com.wugui.pojo.JobInfo;
import com.wugui.service.JobInfoService;
import com.wugui.task.processor.HITAProcessor;
import com.wugui.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
* HITA 数据集入库
*
* @author: huzekang
* @Date: 2019-06-01
*/
@Component
public class HITADatasetJPAPipeline implements Pipeline {

	
	@Override
	public void process(ResultItems resultItems, Task task) {
		DatasetDao datasetDao = SpringUtil.getBean(DatasetDao.class);
		DatasetContentDao datasetContentDao = SpringUtil.getBean(DatasetContentDao.class);
		DatasetContentElementDao datasetContentElementDao = SpringUtil.getBean(DatasetContentElementDao.class);

		resultItems.getAll().forEach((k,v)->{
			if (k.equals(HITAProcessor.KEY_DATASET_LIST)) {
				List<Dataset> list = (List<Dataset>) v;
				datasetDao.saveAll(list);
			} else if (k.equals(HITAProcessor.KEY_DATASET_CONTENT)) {
				DatasetContent datasetContent =(DatasetContent) v;
				datasetContentDao.save(datasetContent);
			} else if (k.equals(HITAProcessor.KEY_DATASET_CONTENT_ELEMENT_LIST)) {
				List<DatasetContentElement> list = (List<DatasetContentElement>) v;
				datasetContentElementDao.saveAll(list);
			}
		});


	}

}
