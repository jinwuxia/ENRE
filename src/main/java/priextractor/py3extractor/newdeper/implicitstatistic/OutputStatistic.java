package priextractor.py3extractor.newdeper.implicitstatistic;

import uerr.SingleCollect;
import util.Configure;
import writer.CsvWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputStatistic {
    private SingleCollect singleCollectInstance = SingleCollect.getSingleCollectInstance();
    private StatisticMember statisticMember;


    public OutputStatistic (StatisticMember statisticMember) {
        this.statisticMember = statisticMember;
    }

    /**
     * output table [methodname, classnameList, class_count]
     * output table [class_count, method_count]
     */
    public void doSummary() {
        ArrayList<String[]> methodDetailArray = doSummaryForMethod();
        ArrayList<String[]> fieldDetailArray = doSummaryForField();
        ArrayList<String[]> methodTypeSummary = statisByClassType(methodDetailArray);
        ArrayList<String[]> fieldTypeSummary = statisByClassType(fieldDetailArray);


        Configure configure = Configure.getConfigureInstance();
        String path = configure.getAnalyzedProjectName();
        CsvWriter csvWriter = new CsvWriter();

        //output table [methodname, classnameList, class_count]
        csvWriter.writeCsv(methodDetailArray, path + "_implicit_pub_func_sameid.csv");

        //output table [fieldname, classnameList, class_count]
        csvWriter.writeCsv(fieldDetailArray, path + "_implicit_pub_var_sameid.csv");

        //output table[class_count, method_count]
        csvWriter.writeCsv(methodTypeSummary, path + "_implicit_pub_func_type.csv");

        //output table[class_count, field_count]
        csvWriter.writeCsv(fieldTypeSummary, path + "_implicit_pub_var_type.csv");
    }

    /**
     * get list [methodname, classnameList, class_count]
     */
    private ArrayList<String[]> doSummaryForMethod() {
        ArrayList<String[]> summaryDetail = new ArrayList<>();
        for (Map.Entry<String, Map<Integer, ArrayList<Integer>>> entry : statisticMember.getMethodName2ClassMap().entrySet()) {
            String methodName = entry.getKey();
            for (Map.Entry<Integer, ArrayList<Integer>> entry1 : entry.getValue().entrySet()) {
                ArrayList<Integer> classIdList = entry1.getValue();
                String classNameListStr = transformList(classIdList);
                int class_count = classIdList.size();

                //update summary_detail
                String[] strList = new String[]{methodName, classNameListStr, Integer.toString(class_count)};
                summaryDetail.add(strList);

            }
        }
        return summaryDetail;
    }


    /**
     * get list [fieldname, classnameList, class_count]
     */
    private ArrayList<String[]> doSummaryForField() {
        ArrayList<String[]> summaryDetail = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : statisticMember.getFieldName2ClassMap().entrySet()) {
            String fieldName = entry.getKey();
            List<Integer> classIdList = entry.getValue();
            String classNameListStr = transformList(classIdList);
            int class_count = classIdList.size();

            //update summary_detail
            String[] strList = new String[]{fieldName, classNameListStr, Integer.toString(class_count)};
            summaryDetail.add(strList);
        }
        return summaryDetail;
    }

    /**
     * from classIdList, get the classnameList_str
     * @param classIdList
     * @return
     */
    private String transformList(List<Integer> classIdList) {
        ArrayList<String> classNameList = new ArrayList<>();
        for (int classId : classIdList) {
            String className = singleCollectInstance.getEntityById(classId).getName();
            classNameList.add(className);
        }
        String classNameListStr = String.join(";", classNameList);
        return classNameListStr;
    }


    /**
     * get [class_count,  method_count]
     * for the method type belonging class_count type,  there are method_count methods.
     * @param detailArray
     * @return
     */
    private ArrayList<String[]> statisByClassType(ArrayList<String[]> detailArray) {
        Map<Integer, Integer> res = new HashMap<>();
        for (String[] row : detailArray) {
            //String fieldOrMethodName = row[0];
            int class_count =  Integer.parseInt(row[2]);
            if (!res.containsKey(class_count)) {
                res.put(class_count, 0);
            }
            res.put(class_count, res.get(class_count) + 1);
        }

        ArrayList<String[]> arrayList = new ArrayList<>();
        String[] titles = new String[]{"P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9", "P10", "P>10"};
        arrayList.add(titles);

        int[] values = new int[]{0,0,0,0,0,0,0,0,0,0,0};
        for(Map.Entry<Integer, Integer> entry : res.entrySet()) {
            int class_count = entry.getKey(); //type=P1,...
            if(class_count >= 11) {
                class_count = 11;
            }
            int index = class_count - 1;
            values[index] += entry.getValue(); //the number of vars
        }
        String [] valueStrs = new String[values.length];
        for (int i = 0; i < valueStrs.length; i++) {
            valueStrs[i] = Integer.toString(values[i]);
        }
        arrayList.add(valueStrs);
        return arrayList;
    }



    private void printArray(ArrayList<String[]> arrayList) {
        for (String[] row : arrayList) {
            System.out.println(String.join(",", row));
        }
    }

}
