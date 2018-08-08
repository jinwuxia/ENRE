package search;

import entity.FunctionEntity;
import entity.VarEntity;
import util.ConstantString;
import visitor.SingleCollect;

import java.util.ArrayList;
import java.util.Map;

public class NameSearchFunction {

    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    /**
     * judge a name inside a function is a packageName or not.
     * @param name
     * @param functionId
     * @return
     */
    public boolean isPackageName(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return false;
        }
        Map<String, String> name2RoleMap = ((FunctionEntity) singleCollect.getEntities().get(functionId)).getName2RoleMap();
        if(name2RoleMap.containsKey(name)){
            if (name2RoleMap.get(name).equals(ConstantString.OPERAND_NAME_ROLE_PKG)) {
                return true;
            }
        }
        return false;
    }


    /**
     * judge a name inside a function is a parameter or not.
     * @param name
     * @param functionId
     * @return
     */
    public boolean isParameterName(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return false;
        }
        Map<String, String> name2RoleMap = ((FunctionEntity) singleCollect.getEntities().get(functionId)).getName2RoleMap();
        if(name2RoleMap.containsKey(name)){
            if (name2RoleMap.get(name).equals(ConstantString.OPERAND_NAME_ROLE_PAR)) {
                return true;
            }
        }
        return false;
    }


    /**
     * judge a name inside a function is a return or not.
     * @param name
     * @param functionId
     * @return
     */
    public boolean isReturnName(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return false;
        }
        Map<String, String> name2RoleMap = ((FunctionEntity) singleCollect.getEntities().get(functionId)).getName2RoleMap();
        if(name2RoleMap.containsKey(name)){
            if (name2RoleMap.get(name).equals(ConstantString.OPERAND_NAME_ROLE_RET)) {
                return true;
            }
        }
        return false;
    }


    /**
     * judge a name inside a function is a function or not.
     * @param name
     * @param functionId
     * @return
     */
    public boolean isFunctionName(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return false;
        }
        Map<String, String> name2RoleMap = ((FunctionEntity) singleCollect.getEntities().get(functionId)).getName2RoleMap();
        if(name2RoleMap.containsKey(name)){
            if (name2RoleMap.get(name).equals(ConstantString.OPERAND_NAME_ROLE_FUN)) {
                return true;
            }
        }
        return false;
    }

    /**
     * judge a name inside a function is a local var/constant or not.
     * @param name
     * @param functionId
     * @return
     */
    public boolean isLocalName(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return false;
        }
        Map<String, String> name2RoleMap = ((FunctionEntity) singleCollect.getEntities().get(functionId)).getName2RoleMap();
        if(name2RoleMap.containsKey(name)){
            if (name2RoleMap.get(name).equals(ConstantString.OPERAND_NAME_ROLE_LOC_VAR)) {
                return true;
            }
        }
        return false;
    }

    /**
     * judge a name inside a function is a global var/constant or not.
     * @param name
     * @param functionId
     * @return
     */
    public boolean isGlobalName(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return false;
        }
        Map<String, String> name2RoleMap = ((FunctionEntity) singleCollect.getEntities().get(functionId)).getName2RoleMap();
        if(name2RoleMap.containsKey(name)){
            if (name2RoleMap.get(name).equals(ConstantString.OPERAND_NAME_ROLE_GLO_VAR)) {
                return true;
            }
        }
        return false;
    }


    /**
     * judge a name is a local/global/parameter/return or not
     * @param name
     * @param functionId
     * @return
     */
    public boolean isVarName(String name, int functionId) {
        if(isLocalName(name, functionId)
                || isGlobalName(name, functionId)
                || isParameterName(name, functionId)
                || isReturnName(name, functionId)
                ) {
            return true;
        }
        return false;
    }

    /**
     * get the id by name inside the functionId
     * @param name
     * @param functionId
     * @return
     */
    public int getIdByName(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return -1;
        }
        Map<String, Integer> name2IdMap = ((FunctionEntity) singleCollect.getEntities().get(functionId)).getName2IdMap();
        if(name2IdMap.containsKey(name)) {
            return name2IdMap.get(name);
        }
        return -1;
    }




}
