package org.minbox.framework.api.boot.cbrc.stateverify.common.utils;/**
 * @author: xiongfei
 * @date: 2022/09/06 21:48
 */

import org.apache.pdfbox.pdmodel.common.PDRectangle;
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
public class PdfUtils {
    //private static final String FONT_PATH = "C:\\Windows\\Fonts\\simfang.ttf";
    //private static final String FONT_PATH2 = "C:\\Windows\\Fonts\\msyh.ttf";
    private static final String OUTPUT_PATH = "E:\\pdf\\test\\component\\table\\";


    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        String filePath = OUTPUT_PATH + "yinjian.pdf";
        List<XEasyPdfRow> rowList = new ArrayList<XEasyPdfRow>(50);
        List<XEasyPdfCell> cellList;

        List<XEasyPdfRow> titleList = new ArrayList<XEasyPdfRow>(12);

        List<XEasyPdfCell> cell1List = new ArrayList<>(12);
        for (int j = 0; j < 12; j++) {
            cell1List.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F).addContent(
                    XEasyPdfHandler.Text.build("我是title")).setBackgroundColor(new Color(0, 191, 255))
            );
        }
        titleList.add(XEasyPdfHandler.Table.Row.build(cell1List));


        for (int i = 0; i < 100; i++) {
            cellList = new ArrayList<>(12);

            for (int j = 0; j < 12; j++) {
//                if (i == 0) {
//                    cell1List.add(XEasyPdfHandler.Table.Row.Cell.build(60F, 30F).addContent(
//                            XEasyPdfHandler.Text.build("我是第一行第" + j + "列")).setBackgroundColor(new Color(0, 191, 255))
//                    );

 //               } else {
                    cellList.add(
//                            i % 2 == 0 ?
//                                    XEasyPdfHandler.Table.Row.Cell.build(60F, 30F).addContent(
//                                            XEasyPdfHandler.Text.build("row" + i + "-cell" + j + "中文中文中文中文中文中文中文中文中文中文中文中文")
//                                    ).setBackgroundColor(new Color(0, 191, 255)) :
                            XEasyPdfHandler.Table.Row.Cell.build(60F, 30F).addContent(
                                    XEasyPdfHandler.Text.build("row" + i + "-cell" + j + "中文中文中文中文中文中文中文中文中文中文中文中文")
                            )
                    );
              //  }
            }
            rowList.add(XEasyPdfHandler.Table.Row.build(cellList));

        }

        XEasyPdfHandler.Document.build().setGlobalHeader(
                XEasyPdfHandler.Header.build(
                        XEasyPdfHandler.Text.build(
                                "中国银行账户交易明细"
                        ).setFontSize(20F).setHorizontalStyle(XEasyPdfPositionStyle.CENTER).setMarginTop(20F)
                )
        ).setGlobalFooter(
                XEasyPdfHandler.Footer.build(
                        XEasyPdfHandler.Text.build(
                                "第" + XEasyPdfHandler.Page.getCurrentPagePlaceholder() + "页，共" + XEasyPdfHandler.Page.getTotalPagePlaceholder() + "页"
                        ).setFontSize(10F).setHorizontalStyle(XEasyPdfPositionStyle.CENTER)
                )
        ).addPage(
                XEasyPdfHandler.Page.build(

                        XEasyPdfPageRectangle.create(PDRectangle.A3.getWidth(), PDRectangle.A3.getHeight()),
                        XEasyPdfHandler.Text.build("账户名称：" + "XXXXX有限公司").setMarginLeft(20F),
                        XEasyPdfHandler.Text.build("账号：" + "XXXXXXXXXXX").setMarginLeft(20F),
                        XEasyPdfHandler.Text.build("日期范围：" + "2021/01/01 --- 2022/01/01").setMarginLeft(20F),
                        XEasyPdfHandler.Text.build("币种：" + "人民币").setMarginLeft(20F),

                        XEasyPdfHandler.Table.build(rowList).setFontSize(5F).setHorizontalStyle(XEasyPdfPositionStyle.CENTER)
                        .setVerticalStyle(XEasyPdfPositionStyle.CENTER).setMarginTop(50F)
                        .setMarginLeft(50F).setMarginBottom(30F)
                        .setTitle(
                                XEasyPdfHandler.Table.build(
                                        XEasyPdfHandler.Table.Row.build(
                                                cell1List
                                        ).setBackgroundColor(Color.GRAY).setMarginLeft(50F)
                                )
                        )
                )
        ).enableReplaceTotalPagePlaceholder()

                .save(filePath).close();
        long end = System.currentTimeMillis();
        System.out.println("finish，耗时：" + (end - begin) + " ms");
    }
}
