package com.jgw.supercodeplatform.trace.dao.sqlbuilder;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.jgw.supercodeplatform.trace.pojo.TraceFunFieldConfig;

public class TraceFunFieldConfigProvider {
  public String batchInsert(Map arg0  ) {

      List<TraceFunFieldConfig> list = (List<TraceFunFieldConfig>)arg0.get("list");
	  StringBuilder sb = new StringBuilder();
      sb.append("INSERT INTO trace_fun_config ");  
      sb.append("(Id,FunctionId,ObjectFieldId,FunctionName,EnTableName,TypeClass,ObjectType,ExtraCreate,TraceTemplateId,FieldType,FieldWeight,FieldName,FieldCode,DefaultValue,IsRequired,ValidateFormat,MinSize,MaxSize,RequiredNumber,MinNumber,MaxNumber,DataValue,IsRemarkEnable,ShowHidden,CreateBy,CreateTime,LastUpdateBy,LastUpdateTime) ");  
      sb.append("VALUES ");  
      MessageFormat mf = new MessageFormat("(null, #'{'list[{0}].functionId}, #'{'list[{0}].objectFieldId}, #'{'list[{0}].functionName}, #'{'list[{0}].enTableName}, #'{'list[{0}].typeClass}, #'{'list[{0}].objectType}, #'{'list[{0}].extraCreate},#'{'list[{0}].traceTemplateId}, #'{'list[{0}].fieldType}, #'{'list[{0}].fieldWeight}, #'{'list[{0}].fieldName}, #'{'list[{0}].fieldCode}"
      		+ ",#'{'list[{0}].defaultValue},#'{'list[{0}].isRequired},#'{'list[{0}].validateFormat},#'{'list[{0}].minSize},#'{'list[{0}].maxSize},#'{'list[{0}].requiredNumber}"
      		+ ",#'{'list[{0}].minNumber},#'{'list[{0}].maxNumber},#'{'list[{0}].dataValue},#'{'list[{0}].isRemarkEnable},#'{'list[{0}].showHidden},#'{'list[{0}].createBy},now(),#'{'list[{0}].lastUpdateBy},now())");  
      for (int i = 0; i < list.size(); i++) {  
          sb.append(mf.format(new Object[]{i}));  
          if (i < list.size() - 1) {  
              sb.append(",");  
          }  
      }  
      String sql=sb.toString();
      return sql;  
  }
  
}
