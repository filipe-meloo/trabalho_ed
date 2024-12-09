/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imf.simulation;

import imf.entity.SimulationManual;
import imf.entity.Division;
import imf.entity.Mission;
import imf.exception.InvalidFileException;
import imf.exception.NullException;
import java.util.Iterator;
import java.util.Scanner;
import listOrderedUnordered.ArrayUnorderedList;
import queue.LinkedQueue;

/**
 * Estrutura de Dados - 2020-2021.
 *
 * @author Mariana Ribeiro - 8190573
 * @author André Raro - 8190590
 *
 * Classe Manual representa a simulação manual, deve pedir ao TóCruz a qual a
 * entrada a considerar e a partir daí, iterativamente, pedir a divisão para a
 * qual deseja movimentar-se.
 */
public class Manual {

    /**
     * Vida inicial do Tó Cruz
     */
    private static Double LIFE_DEFAULT = 100.0;
    /**
     * String utilizada para tornar o output mais perceptível
     */
    private static String S_AUX = "\n*-*-*-*-*-*-*-*-*\n";
    /**
     * LinkedQueue que guarda o caminho percorrido na simulação manual
     */
    private LinkedQueue<Division> path;
    /**
     * Variável do tipo mission utilizada para guardar informações básicas da
     * missão
     */
    private Mission mission;
    /**
     * Vida atual do Tó Cruz
     */
    private double lifePoints;
    /**
     * Flag utilizada para saber se o Tó Cruz ainda está dentro do edifício
     */
    boolean flagLeft;
    /**
     * Flag utilizada para saber se o Tó Cruz já adquiriu o alvo da missão
     */
    boolean flagTarget;
    /**
     * Variável do tipo divisão que guarda a divisão atual
     */
    Division currentDiv;

    /**
     * Construtor que cria uma simulação manual e inicializa as variáveis. O
     * parametro missão é recebido para serem guardadas as simulações geradas.
     *
     * @param mission missão
     */
    public Manual(Mission mission) {
        this.mission = mission;
        this.lifePoints = this.LIFE_DEFAULT;
        this.flagLeft = false;
        this.flagTarget = false;
        this.currentDiv = new Division();
        this.path = new LinkedQueue();
    }

    /**
     * Função para escolher a divisão inicial do Tó Cruz sendo que esta tem que
     * ser uma entrada/saída.
     *
     * @throws InvalidFileException caso nao seja válido
     */
    public void start() throws InvalidFileException {
        System.out.println(smsInicial());
        String s = "Escolha um local para começar a missão escrevendo um dos seguintes nomes:\n";
        Iterator<Division> it = mission.getExitEntry().iterator();
        while (it.hasNext()) {
            s += it.next().getName();
            if (it.hasNext()) {
                s += (" OU ");
            }
        }
        System.out.println(s);

        boolean flagOpt = false;
        Scanner scan = new Scanner(System.in, "ISO-8859-1");
        do {
            String opt = scan.nextLine();
            if (mission.getExitEntry().contains(mission.getDivisionExitEntry(opt))) {
                currentDiv = mission.getDivisionExitEntry(opt);
                path.enqueue(currentDiv);
                flagOpt = true;
                lifePoints -= (currentDiv.getEnemiesPower());
                System.out.print(this.S_AUX + "Pontos de Vida: " + lifePoints + "\nDivisão Atual: " + currentDiv.getName() + "\n");
                break;
            }
            if (flagOpt == false) {
                System.out.println("--Não é um valor válido!");
            }
        } while (flagOpt == false);
        game();
        SimulationManual newSimulation = new SimulationManual(lifePoints, path, flagTarget);
        try {
            mission.addSimulation(newSimulation);
        } catch (NullException ex) {
            System.out.println("Não foi possivel adicionar a simulação manual a missão: " + ex.getMessage());
        }
    }

    /**
     * Função onde decorre o resto do jogo. O utilizador escolhe uma divisão e
     * são retornadas as suas ligações.
     */
    private void game() {
        String target = mission.getTarget().getDivision();

        while (lifePoints > 0 && flagLeft == false) {
            String s = "Escolha a próxima divisão escrevendo um dos seguintes nomes:\n";
            ArrayUnorderedList<String> connections = currentDiv.getEdges();
            Iterator<String> itConnections = connections.iterator();
            String current = null;
            while (itConnections.hasNext()) {
                current = itConnections.next();
                s += current;
                if (itConnections.hasNext()) {
                    s += (" OU ");
                }
            }
            System.out.println(s);

            boolean flagOpt = false;
            Scanner scan = new Scanner(System.in, "ISO-8859-1");
            do {
                String opt = scan.nextLine();
                if (connections.contains(opt)) {
                    currentDiv = mission.getDivision(opt);
                    path.enqueue(currentDiv);
                    flagOpt = true;
                    lifePoints -= (currentDiv.getEnemiesPower());
                    if (lifePoints > 0) {
                        System.out.print(this.S_AUX + "Pontos de Vida: " + lifePoints + "\nDivisão Atual: " + currentDiv.getName() + "\n");
                    } else {
                        System.out.print(this.S_AUX + "Pontos de Vida: 0 \n Infelizmente a Missão Falhou!\n");
                    }
                    if (target.equals(opt) && flagTarget == false) {
                        flagTarget = true;
                        System.out.print(this.S_AUX + "Alvo adquirido com sucesso!\nEscape do edifício com vida!\n");
                    }
                    if (flagTarget == true && currentDiv.isEntryExit()) {
                        System.out.print(this.S_AUX + "Missão Concluída Com Sucesso!\n*-*-Parabéns Tó Cruz!!-*-*\n");
                        flagLeft = true;
                    }
                    if (flagTarget == false && currentDiv.isEntryExit() && lifePoints > 0) {
                        System.out.print("Não tem o alvo!\nQuer mesmo sair?\n  Responda ' Sim ' se pretender abandonar a missão caso contrário escreva qualquer coisa \n");

                        String optLeft = scan.nextLine();
                        if ("Sim".equalsIgnoreCase(optLeft)) {
                            System.out.print(this.S_AUX + "Infelizmente a Missão Falhou!\n");
                            flagLeft = true;
                        } else {
                            flagLeft = false;
                        }
                    }
                    break;
                }
                if (flagOpt == false) {
                    System.out.println("--Não é um valor válido!");
                }
            } while (flagOpt == false);
        }
        System.out.println(smsFinal());
    }

    /**
     * String s que contém a informação relativa à missão
     *
     * @return String s com a informação
     */
    private String smsInicial() {
        String s = "*-*-*-*-*-*-*--*-*-*-*--*-*-*-*--*-*-*--*-*-*--*-*-*-*--*-*-*-*-*-*-*\n";
        s += "\t  Simulação : Manual \n";
        s += "\t  Missão: \n";
        s += "\t\tCodigo-Missão:" + mission.getCod() + "\n";
        s += "\t\tVersão:" + mission.getVersion() + "\n";
        s += "\t\tAlvo:\n";
        s += "\t\t\tDivisão:" + mission.getTarget().getDivision() + "\n";
        s += "\t\t\tTipo:" + mission.getTarget().getType() + "\n";
        s += "*-*-*-*-*-*-*--*-*-*-*--*-*-*-*--*-*-*--*-*-*--*-*-*-*-*-*-*-*--*-*-*\n";

        return s;
    }

    /**
     * String s que contém a informação relativa ao caminho percorrido
     *
     * @return String s com o caminho percorrido
     */
    private String smsFinal() {
        String s = "";
        LinkedQueue<Division> pathTemp = new LinkedQueue<Division>();
        Division current = null;

        s += "Caminho:\n";
        while (!path.isEmpty()) {
            current = (Division) path.dequeue();
            pathTemp.enqueue(current);
            s += "\t-> " + current.getName() + "\n";
        }

        this.path = pathTemp;

        return s;
    }

}
