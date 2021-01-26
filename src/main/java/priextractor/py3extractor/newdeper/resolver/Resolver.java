package priextractor.py3extractor.newdeper.resolver;

import expression.ExpressionCollect;
import priextractor.py3extractor.searcher.NameSearch;
import uerr.SingleCollect;

public interface Resolver {
    ExpressionCollect expressionCollect = ExpressionCollect.getExpressionCollect();
    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    NameSearch nameSearch = NameSearch.getNameSearchInstance();

    void resolve();
}
