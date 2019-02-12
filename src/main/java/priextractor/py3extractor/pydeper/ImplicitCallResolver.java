package priextractor.py3extractor.pydeper;

import uerr.SingleCollect;

import java.util.ArrayList;

public class ImplicitCallResolver {

    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    /**
     *
     * @param modOrFuncId
     * @param calleeStr    original callee Str
     * @param simpleCalleeStr  callee str which contains only one "()"
     * @return
     */
    public ArrayList<Integer> resolveCallee(int modOrFuncId, String calleeStr, String simpleCalleeStr) {
        String[] tmp = simpleCalleeStr.split("\\(")[0].split("\\.");
        String pureCalleeName = tmp[tmp.length - 1]; //without dot

        ArrayList<Integer> ids = singleCollect.searchFunctionByName(pureCalleeName);

        return ids;
    }
}
