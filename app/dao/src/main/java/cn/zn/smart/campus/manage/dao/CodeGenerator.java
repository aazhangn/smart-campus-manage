package cn.zn.smart.campus.manage.dao;

import cn.zn.smart.campus.manage.dao.po.base.BaseLogicDeletePo;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * @Description: mybatis-plus代码自动生成
 * @Author: zhangnan
 * @Date: 2021/05/13 10:32
 */
@Slf4j
public class CodeGenerator {
    private static final String MODULE_NAME = "dao";
    private static final String AUTHOR = "zhangnan";
    private static final String DATA_SOURCE_URL= "jdbc:mysql://175.24.232.164:3306/smart_campus?useUnicode=true" +
            "&useSSL=false&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "wangpeng123";
    private static final Class SUPER_ENTITY_CLASS = BaseLogicDeletePo.class;

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/app/dao" +"/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        //覆盖已有文件
        gc.setFileOverride(true);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DATA_SOURCE_URL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            /**
             * 自定义数据库表字段类型转换【可选】
             *
             * @param globalConfig globalConfig
             * @param fieldType    数据库字段类型
             * @return IColumnType
             */
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                log.info(fieldType);
                if (StringUtils.containsIgnoreCase(fieldType, "datetime") ||
                        StringUtils.containsIgnoreCase(fieldType, "date")) {
                    return DbColumnType.DATE;
                } else if (StringUtils.containsIgnoreCase(fieldType, "decimal")) {
                    return DbColumnType.DOUBLE;
                } else {
                    return super.processTypeConvert(globalConfig, fieldType);
                }
            }
        });
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(MODULE_NAME);
        pc.setParent("cn.zn.smart.campus.manage");
        pc.setEntity("po");
        pc.setMapper("mapper");
        pc.setXml("xml");
        mpg.setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //不生成controller
        templateConfig.setController("");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(SUPER_ENTITY_CLASS);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id", "extra", "deleted", "create_time", "modified_time");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        //模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
