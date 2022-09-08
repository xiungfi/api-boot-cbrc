package org.minbox.framework.api.boot.cbrc.stateverify.common.utils;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.minbox.framework.api.boot.cbrc.stateverify.entity.SystemUser;
import wiki.xsx.core.pdf.component.table.XEasyPdfCell;
import wiki.xsx.core.pdf.component.table.XEasyPdfRow;
import wiki.xsx.core.pdf.doc.XEasyPdfPageRectangle;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;
import wiki.xsx.core.pdf.handler.XEasyPdfHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiongfei
 * @date: 2022/09/06 21:48
 */
public class PdfFileUtils {

   //  @Value("statement.file.save.path")
    private static String filePath="E:\\pdf\\test\\component\\table\\";

    //  @Value("statement.file.save.path")
    // private static String fileName="pdftest.pdf";

    public static void generatePdfFile(String fileName,String detailStartDate, String detailEndDate, List<String> titles,
        List<SystemUser> detailList) {
        long begin = System.currentTimeMillis();
        String filePath = PdfFileUtils.filePath + fileName;
        List<XEasyPdfRow> rowList = new ArrayList<XEasyPdfRow>();
        List<XEasyPdfCell> cellList;

        // List<XEasyPdfRow> titleList = new ArrayList<XEasyPdfRow>();

        List<XEasyPdfCell> titleCells = new ArrayList<>();
        for (int j = 0; j < titles.size(); j++) {
            titleCells.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(titles.get(j))).setBackgroundColor(new Color(0, 191, 255)));
        }
        // titleList.add(XEasyPdfHandler.Table.Row.build(titleCells));

        for (int i = 0; i < detailList.size(); i++) {
            cellList = new ArrayList<>();

            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUserId())));

            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUsername())));

            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUserId())));

            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUsername())));
            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUserId())));

            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUsername())));
            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUserId())));

            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUsername())));
            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUserId())));

            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUsername())));
            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUserId())));

            cellList.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F)
                .addContent(XEasyPdfHandler.Text.build(detailList.get(i).getUsername())));

            rowList.add(XEasyPdfHandler.Table.Row.build(cellList));

        }

        XEasyPdfHandler.Document.build()
            .setGlobalHeader(XEasyPdfHandler.Header.build(XEasyPdfHandler.Text.build("中国银行账户交易明细").setFontSize(20F)
                .setHorizontalStyle(XEasyPdfPositionStyle.CENTER).setMarginTop(20F)))
            .setGlobalFooter(XEasyPdfHandler.Footer.build(XEasyPdfHandler.Text
                .build("第" + XEasyPdfHandler.Page.getCurrentPagePlaceholder() + "页，共"
                    + XEasyPdfHandler.Page.getTotalPagePlaceholder() + "页")
                .setFontSize(10F).setHorizontalStyle(XEasyPdfPositionStyle.CENTER)))
            .addPage(XEasyPdfHandler.Page.build(

                XEasyPdfPageRectangle.create(PDRectangle.A3.getWidth(), PDRectangle.A3.getHeight()),
                XEasyPdfHandler.Text.build("账户名称：" + detailList.get(0).getUserId()).setMarginLeft(20F),
                XEasyPdfHandler.Text.build("账号：" + detailList.get(0).getUsername()).setMarginLeft(20F),
                XEasyPdfHandler.Text.build("日期范围：" + detailStartDate + "---" + detailEndDate).setMarginLeft(20F),
                XEasyPdfHandler.Text.build("币种：" + "人民币").setMarginLeft(20F),

                XEasyPdfHandler.Table.build(rowList).setFontSize(5F).setHorizontalStyle(XEasyPdfPositionStyle.CENTER)
                    .setVerticalStyle(XEasyPdfPositionStyle.CENTER).setMarginTop(50F).setMarginLeft(50F)
                    .setMarginBottom(30F)
                    .setTitle(XEasyPdfHandler.Table.build(XEasyPdfHandler.Table.Row.build(titleCells)
                        .setBackgroundColor(Color.GRAY).setMarginLeft(50F)))))
            .enableReplaceTotalPagePlaceholder()

            .save(filePath).close();
        long end = System.currentTimeMillis();
        System.out.println("finish，耗时：" + (end - begin) + " ms");
    }

    public static void main(String[] args) {

        List<String> titles = new ArrayList<>();
        titles.add("title1");
        titles.add("title2");
        titles.add("title3");
        titles.add("title4");
        titles.add("title5");
        titles.add("title6");
        titles.add("title7");
        titles.add("title8");
        titles.add("title9");
        titles.add("title10");
        titles.add("title11");
        titles.add("title12");
        List<SystemUser> detailList =new ArrayList<>();
        for(int i=0;i<100;i++){
            detailList.add(new SystemUser().setUserId(i+"号").setUsername(i+"--name"));
        }
        generatePdfFile("testpdf.pdf","2020/01/01","2020/01/05",titles,detailList);

    }
}
