package priextractor.py3extractor.newdeper;

import priextractor.py3extractor.newdeper.implicitstatistic.AtomResoveSummary;
import priextractor.py3extractor.newdeper.resolver.*;

public class ResolverTemplate {

    public void run() {
        //SingleCollect.getSingleCollectInstance().printAllEntity();


        ExpResolverOfAtom atomResolve = new ExpResolverOfAtom();
        atomResolve.resolve();
        //System.out.println("\nafter explicit resolve...");
        //AtomResoveSummary atomResoveSummary = new AtomResoveSummary();
        //atomResoveSummary.doSummary();

        //prepare for implicit inference
        ImpResolverSetup impResolverSetup = new ImpResolverSetup();
        ImpResolverOfAtom atomResolverImp = new ImpResolverOfAtom(impResolverSetup);
        atomResolverImp.resolve();
        ResolverOfRefinement refineResolver = new ResolverOfRefinement();
        refineResolver.resolve();
        ResolverOfAbstraction resolverOfAbstraction  = new ResolverOfAbstraction();
        resolverOfAbstraction.resolve();
        UnknownResolver unknownResolver = new UnknownResolver();
        unknownResolver.resolve();

        System.out.println("\n final resolve result...");
        AtomResoveSummary atomResoveSummary = new AtomResoveSummary();
        atomResoveSummary.doSummary();

        //atomResoveSummary.summarizeFinalPossibeOnes();
        //atomResoveSummary.summaryUnknown();

        //System.out.println("\nresults are in the following....");
        //ExpressionCollect.getExpressionCollect().printAllAtoms();


    }
}
