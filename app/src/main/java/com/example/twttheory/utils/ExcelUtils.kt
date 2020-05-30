package com.example.twttheory.utils

import android.util.ArrayMap
import jxl.Workbook
import java.io.File

object ExcelUtils {
    fun xlsJxl(file: File): Map<String, List<List<String>>> {
        val result = ArrayMap<String, List<List<String>>>()
        Workbook.getWorkbook(file).sheets.forEach { sheet ->
            val rowsList = ArrayList<List<String>>()//列数据
            val sheetName = sheet.name
            for (i in 0 until sheet.rows) {
                rowsList.add(
                    sheet.getRow(i).map { it.contents }
                )
            }
            result[sheetName] = rowsList
        }
        return result
    }

}