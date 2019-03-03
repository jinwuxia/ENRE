package priextractor.py3extractor.newdeper;

import priextractor.py3extractor.newdeper.implicitstatistic.AtomResoveSummary;

public class ResolverTemplate {

    public void run() {
        //SingleCollect.getSingleCollectInstance().printAllEntity();


        ExpResolverOfAtom atomResolve = new ExpResolverOfAtom();
        atomResolve.resolve();
        System.out.println("\nafter explicit resolve...");
        AtomResoveSummary atomResoveSummary = new AtomResoveSummary();
        atomResoveSummary.doSummary();



        //prepare for implicit inference
        ImpResolverSetup impResolverSetup = new ImpResolverSetup();
        ImpResolverOfAtom atomResolverImp = new ImpResolverOfAtom(impResolverSetup);
        atomResolverImp.resolve();
        System.out.println("\nafter implicit resolve...");
        atomResoveSummary = new AtomResoveSummary();
        atomResoveSummary.doSummary();


        ResolverOfRefinement refineResolver = new ResolverOfRefinement();
        refineResolver.resolve();
        System.out.println("\nafter refinement resolve...");
        atomResoveSummary = new AtomResoveSummary();
        atomResoveSummary.doSummary();

        ResolverOfAbstraction resolverOfAbstraction  = new ResolverOfAbstraction();
        resolverOfAbstraction.resolve();
        System.out.println("\nafter abstraction resolve...");
        atomResoveSummary = new AtomResoveSummary();
        atomResoveSummary.doSummary();


        UnknownResolver unknownResolver = new UnknownResolver();
        unknownResolver.resolve();
        System.out.println("\nafter unknown resolve...");
        atomResoveSummary = new AtomResoveSummary();
        atomResoveSummary.doSummary();

        atomResoveSummary.summarizeFinalPossibeOnes();
        atomResoveSummary.summaryUnknown();

        //System.out.println("\nresults are in the following....");
        //ExpressionCollect.getExpressionCollect().printAllAtoms();


    }
}
