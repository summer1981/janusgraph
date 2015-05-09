package com.thinkaurelius.titan.core;

import com.thinkaurelius.titan.core.schema.TitanManagement;
import org.apache.tinkerpop.gremlin.process.computer.GraphComputer;
import org.apache.tinkerpop.gremlin.structure.Graph;

/**
 * Titan graph database implementation of the Blueprint's interface.
 * Use {@link TitanFactory} to open and configure TitanGraph instances.
 *
 * @author Matthias Br&ouml;cheler (http://www.matthiasb.com)
 * @see TitanFactory
 * @see TitanTransaction
 */
@Graph.OptIn(Graph.OptIn.SUITE_STRUCTURE_STANDARD)
@Graph.OptIn(Graph.OptIn.SUITE_STRUCTURE_PERFORMANCE)
@Graph.OptIn(Graph.OptIn.SUITE_PROCESS_STANDARD)
@Graph.OptIn(Graph.OptIn.SUITE_PROCESS_COMPUTER)
@Graph.OptIn(Graph.OptIn.SUITE_PROCESS_PERFORMANCE)
@Graph.OptIn(Graph.OptIn.SUITE_GROOVY_PROCESS_STANDARD)
@Graph.OptIn(Graph.OptIn.SUITE_GROOVY_PROCESS_COMPUTER)
@Graph.OptIn(Graph.OptIn.SUITE_GROOVY_ENVIRONMENT)
@Graph.OptIn(Graph.OptIn.SUITE_GROOVY_ENVIRONMENT_INTEGRATE)
@Graph.OptIn(Graph.OptIn.SUITE_GROOVY_ENVIRONMENT_PERFORMANCE)
//------------------------
@Graph.OptOut(
        test = "org.apache.tinkerpop.gremlin.structure.EdgeTest$ExceptionConsistencyWhenEdgeRemovedTest",
        method = "shouldThrowExceptionIfEdgeWasRemoved",
        specific = "e.remove()",
        reason = "Titan cannot currently throw an exception on access to removed relations due to internal use.")
@Graph.OptOut(
        test = "org.apache.tinkerpop.gremlin.structure.EdgeTest$ExceptionConsistencyWhenEdgeRemovedTest",
        method = "shouldThrowExceptionIfEdgeWasRemoved",
        specific = "property(k)",
        reason = "Titan cannot currently throw an exception on access to removed relations due to internal use.")
@Graph.OptOut(
        test = "org.apache.tinkerpop.gremlin.structure.EdgeTest$ExceptionConsistencyWhenEdgeRemovedTest",
        method = "shouldThrowExceptionIfEdgeWasRemoved",
        specific = "remove()",
        reason = "Titan cannot currently throw an exception on access to removed relations due to internal use.")
@Graph.OptOut(
        test = "org.apache.tinkerpop.gremlin.structure.VertexPropertyTest$ExceptionConsistencyWhenVertexPropertyRemovedTest",
        method = "shouldThrowExceptionIfVertexPropertyWasRemoved",
        specific = "property(k)",
        reason = "Titan cannot currently throw an exception on access to removed relations due to internal use.")
@Graph.OptOut(
        test = "org.apache.tinkerpop.gremlin.structure.VertexPropertyTest$VertexPropertyAddition",
        method = "shouldHandleSetVertexProperties",
        reason = "Titan can only handle SET cardinality for properties when defined in the schema")
//--------------TODO: Need to be fixed! ----------
//@Graph.OptOut(
//        test="org.apache.tinkerpop.gremlin.process.traversal.strategy.decoration.SubgraphStrategyProcessTest",
//        method="shouldFilterVertexCriterion",
//        reason="Access to adjacent vertex properties which isn't allowed under GraphComputer StarGraph semantics")
//@Graph.OptOut(
//        test="org.apache.tinkerpop.gremlin.process.traversal.strategy.decoration.SubgraphStrategyProcessTest",
//        method="shouldFilterMixedCriteria",
//        reason="Access to adjacent vertex properties which isn't allowed under GraphComputer StarGraph semantics")
//@Graph.OptOut(
//        test="com.tinkerpop.gremlin.process.graph.step.branch.UnionTest$ComputerTest",
//        method="g_V_chooseXlabel_eq_person__unionX__out_lang__out_nameX__in_labelX",
//        reason="TP3 unexpectedly retrieves labels for neighboring vertices when detaching elements")
//@Graph.OptOut(
//        test = "com.tinkerpop.gremlin.structure.strategy.StrategyGraphTest$EdgeShouldBeWrappedTest",
//        method = "shouldWrap",
//        specific = "g.V(4).inE()",
//        reason = "Issue with wrapping/unwrapping vertices and using optimization strategies")
public interface TitanGraph extends TitanGraphTransaction {

   /* ---------------------------------------------------------------
    * Transactions and general admin
    * ---------------------------------------------------------------
    */

    /**
     * Opens a new thread-independent {@link TitanTransaction}.
     * <p/>
     * The transaction is open when it is returned but MUST be explicitly closed by calling {@link com.thinkaurelius.titan.core.TitanTransaction#commit()}
     * or {@link com.thinkaurelius.titan.core.TitanTransaction#rollback()} when it is no longer needed.
     * <p/>
     * Note, that this returns a thread independent transaction object. It is not necessary to call this method
     * to use Blueprint's standard transaction framework which will automatically start a transaction with the first
     * operation on the graph.
     *
     * @return Transaction object representing a transactional context.
     */
    public TitanTransaction newTransaction();

    /**
     * Returns a {@link TransactionBuilder} to construct a new thread-independent {@link TitanTransaction}.
     *
     * @return a new TransactionBuilder
     * @see TransactionBuilder
     * @see #newTransaction()
     */
    public TransactionBuilder buildTransaction();

    /**
     * Returns the management system for this graph instance. The management system provides functionality
     * to change global configuration options, install indexes and inspect the graph schema.
     * <p />
     * The management system operates in its own transactional context which must be explicitly closed.
     *
     * @return
     */
    public TitanManagement openManagement();

    /**
     * Checks whether the graph is open.
     *
     * @return true, if the graph is open, else false.
     * @see #close()
     */
    public boolean isOpen();

    /**
     * Checks whether the graph is closed.
     *
     * @return true, if the graph has been closed, else false
     */
    public boolean isClosed();

    /**
     * Closes the graph database.
     * <p/>
     * Closing the graph database causes a disconnect and possible closing of the underlying storage backend
     * and a release of all occupied resources by this graph database.
     * Closing a graph database requires that all open thread-independent transactions have been closed -
     * otherwise they will be left abandoned.
     *
     * @throws TitanException if closing the graph database caused errors in the storage backend
     */
    @Override
    public void close() throws TitanException;





}
