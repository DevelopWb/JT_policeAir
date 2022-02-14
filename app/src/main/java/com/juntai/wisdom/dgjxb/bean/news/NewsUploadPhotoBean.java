package com.juntai.wisdom.dgjxb.bean.news;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:图片上传返回
 * Create by zhangzhenlong
 * 2020-8-4
 * email:954101549@qq.com
 */
public class NewsUploadPhotoBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * status : 200
     * data : ["https://image.juntaikeji.com/2020-08-01/6f5de06e33e44a7d96b120ee3a80d2e8.jpg","https://image.juntaikeji.com/2020-08-01/59e34a532095483ead3f831e6048db1e.jpg"]
     * type : null
     * message : null
     * success : true
     */

    private List<String> data;

    public List<String> getData() {
        return data == null? new ArrayList<>():data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
