package uerr;

/**
 * LocalBlock is the block inside function or method.
 * package block, file block, function block are coresponding to uerr,
 * so we can use parentId to specify these blocks.
 *
 * But, variables inside a function, have local scope.
 * Such kind of local block is only valid inside function or method body.
 * for example, for-stmt, if-stmt, swith-stmt, other nested block inside a local block.
 *
 * Just like localName, LocalBlock is valid only inside a function/method.
 * So, We bind a LocalBlock list to its functionEntity, please see AbsFUNEntity class declaration.
 */
public class LocalBlock {
    private int id;
    //depth is necessary, it 's the size of current block stack.
    //it is for searching var .
    private int depth;
    private String name;
    private int parentBlockId; // not necessary. when current stack size = 0 (functionbody), its parentBlockID = -1


    public LocalBlock(int id, String name, int parentBlockId, int depth) {
        this.id = id;
        this.depth = depth;
        this.name = name;
        this.parentBlockId = parentBlockId;
    }

    public int getDepth() {
        return depth;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParentBlockId() {
        return parentBlockId;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentBlockId(int parentBlockId) {
        this.parentBlockId = parentBlockId;
    }

    @Override
    public String toString() {
        String str = "";
        str += "LocalBlock(";
        str += ("id:" + id + ",");
        str += ("name:" + name + ",");
        str += ("depth:" + depth + ",");
        str += ("parentBlock:" + parentBlockId);
        str += ")";
        return str;
    }
}
