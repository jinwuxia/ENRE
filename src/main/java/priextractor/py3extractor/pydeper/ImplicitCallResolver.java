package priextractor.py3extractor.pydeper;

import uerr.SingleCollect;

import java.util.ArrayList;

public class ImplicitCallVisitor {

    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    public ArrayList<Integer> resolveCalleeByName(String simpleCalleeStr) {
        String arr[] = simpleCalleeStr.split("\\.");
        String str = arr[arr.length - 1];
        String pureCalleeName = str.split("\\(")[0];

        ArrayList<Integer> ids = singleCollect.searchFunctionByName(pureCalleeName);

        return ids;
    }
}
