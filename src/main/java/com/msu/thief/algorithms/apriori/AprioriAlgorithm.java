package com.msu.thief.algorithms.apriori;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.msu.interfaces.IEvaluator;
import com.msu.model.AbstractSingleObjectiveDomainAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.thief.algorithms.AlgorithmUtil;
import com.msu.thief.algorithms.OnePlusOneEA;
import com.msu.thief.io.thief.reader.BonyadiSingleObjectiveReader;
import com.msu.thief.problems.SingleObjectiveThiefProblem;
import com.msu.thief.variable.TTPVariable;
import com.msu.thief.variable.pack.factory.EmptyPackingListFactory;
import com.msu.thief.variable.tour.Tour;
import com.msu.util.MyRandom;

public class AprioriAlgorithm extends AbstractSingleObjectiveDomainAlgorithm<SingleObjectiveThiefProblem> {

	// final public int NUM_OF_POPULATION = 1000;

	// ! evaluator for this problem. set for every run
	protected IEvaluator eval;

	// ! problem for this run
	protected SingleObjectiveThiefProblem problem;

	// ! random generator
	protected MyRandom rand;

	// ! set for this run
	NonDominatedSolutionSet set;

	@Override
	public Solution run___(SingleObjectiveThiefProblem problem, IEvaluator eval, MyRandom rand) {

		// initialize the variables
		this.problem = problem;
		this.eval = eval;
		this.rand = rand;
		this.set = new NonDominatedSolutionSet();

		// calculate best tours
		Tour<?> tour = AlgorithmUtil.calcBestTour(problem);
		Tour<?> symmetricTour = tour.getSymmetric();

		// all the nodes to work with
		MutableList<AprioriNode> nodes = new FastList<>(Arrays.asList(createRootNode(tour)));
		MutableList<AprioriNode> symmetricNodes = new FastList<>(Arrays.asList(createRootNode(symmetricTour)));

		report(nodes);
		
		// while there are nodes land evaluations
		while (eval.hasNext() && (!nodes.isEmpty() && !symmetricNodes.isEmpty())) {

			nodes = next(nodes, tour);
			symmetricNodes = next(symmetricNodes, symmetricTour);


			report(nodes);
		}

		return set.get(0);
	}

	protected void report(MutableList<AprioriNode> nodes) {
		List<AprioriEntry> entries = nodes.flatCollect(AprioriNode::getChildren);
		Collections.sort(entries,
				(o1, o2) -> Double.compare(o1.solution.getObjectives(0), o2.solution.getObjectives(0)));

		for (AprioriEntry e : entries.subList(0, Math.min(10, entries.size()))) {
			System.out.println(String.format("%s -> %s", e.solution.getObjectives(0), Arrays.toString(e.items.toArray())));
		}
		System.out.println(entries.size());
		System.out.println("---------------------------");

	}
	
	protected MutableList<AprioriNode> next(MutableList<AprioriNode> nodes, Tour<?> tour) {
		MutableList<AprioriNode> next = new FastList<>();
		for (AprioriNode node : nodes) {
			if (!eval.hasNext()) break;
			next.addAll(node.expand(eval, problem, tour, set));
		}
		return next;
	}

	
	
	protected AprioriNode createRootNode(Tour<?> tour) {
		// solution without picking any item
		Solution empty = eval.evaluate(problem,
				new TTPVariable(tour, new EmptyPackingListFactory().next(problem, rand)));
		set.add(empty);

		// create the root apriori node
		AprioriNode root = new AprioriNode();
		for (int i = 0; i < problem.numOfItems(); i++) {
			AprioriEntry entry = new AprioriEntry(i, new HashSet<>(Arrays.asList(i)));
			entry.evaluate(eval, problem, tour);

			// add only if node is better than father
			if (empty.getObjectives(0) > entry.solution.getObjectives(0))
				root.add(entry);
		}
		return root;
	}

	
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();

		SingleObjectiveThiefProblem p = new BonyadiSingleObjectiveReader()
				// .read("../ttp-benchmark/SingleObjective/10/10_5_6_25.txt");
				.read("../ttp-benchmark/SingleObjective/10/10_10_2_50.txt");
				// .read("../ttp-benchmark/SingleObjective/10/10_15_10_75.txt");
				//.read("../ttp-benchmark/SingleObjective/20/20_5_6_75.txt");
		// .read("../ttp-benchmark/SingleObjective/20/20_30_9_25.txt");
		//.read("../ttp-benchmark/SingleObjective/50/50_15_8_50.txt");
		// .read("../ttp-benchmark/SingleObjective/100/100_5_10_50.txt");

		OnePlusOneEA eaSym = new OnePlusOneEA(false);
		eaSym.checkSymmetric = true;
		eaSym.setName("1+1-EA-SYM");

		AprioriAlgorithm heuristic = new AprioriAlgorithm();
		NonDominatedSolutionSet set = heuristic.run(p, new Evaluator(5000000), new MyRandom(123456));

		System.out.println(set);
		System.out.println(
				Arrays.toString(((TTPVariable) set.get(0).getVariable()).getPackingList().toIndexSet().toArray()));

	}

}