package com.gluonhq.javaqc.ch05.quantumcoin;

import com.gluonhq.strange.*;
import com.gluonhq.strange.gate.*;
import com.gluonhq.strange.local.*;
import com.gluonhq.strangefx.render.Renderer;

import javafx.application.Platform;

public class Main {

    private static final int COUNT = 1000;

    public static void main(String[] args) {
        int results[] = new int[4];
        QuantumExecutionEnvironment simulator = new SimpleQuantumExecutionEnvironment();
        Program program = new Program(2);
        Step step1 = new Step();
        step1.addGate(new Hadamard(0));
        step1.addGate(new Hadamard(1));
        program.addStep(step1);
        for (int i = 0; i < COUNT; i++) {
            Result result = simulator.runProgram(program);
            Qubit[] qubits = result.getQubits();
            Qubit zero = qubits[0];
            Qubit one = qubits[1];
            boolean coinA = zero.measure() == 1;
            boolean coinB = one.measure() == 1;
            if (!coinA && !coinB) results[0]++;
            if (!coinA && coinB) results[1]++;
            if (coinA && !coinB) results[2]++;
            if (coinA && coinB) results[3]++;
        }
        System.out.println("We did "+COUNT+" experiments.");
        System.out.println("[AB]: 0 0 occured "+results[0]+" times.");
        System.out.println("[AB]: 0 1 occured "+results[1]+" times.");
        System.out.println("[AB]: 1 0 occured "+results[2]+" times.");
        System.out.println("[AB]: 1 1 occured "+results[3]+" times.");
        System.out.println("We will now render program");

        Renderer.renderProgram(program);
        Renderer.showProbabilities(program, 1000);
        System.out.println("Done rendering, that's all folks.");
    }  

}
