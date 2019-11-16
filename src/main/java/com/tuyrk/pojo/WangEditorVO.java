package com.tuyrk.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/3/5 23:46 星期二
 * Description:
 */
@Data
public class WangEditorVO {
    String errno;
    List<String> data;

    public WangEditorVO(String errno, List<String> data) {
        this.errno = errno;
        this.data = data;
    }

    public WangEditorVO(String errno, String data) {
        this.errno = errno;
        this.data = new ArrayList<>();
        this.data.add(data);
    }
}
