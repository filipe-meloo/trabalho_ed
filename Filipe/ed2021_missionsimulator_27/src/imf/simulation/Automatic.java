/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imf.simulation;

import imf.entity.Division;
import imf.entity.Mission;
import java.util.Iterator;
import queue.LinkedQueue;
import stack.LinkedStack;

/**
 * Estrutura de Dados - 2020-2021.
 *
 * @author Mariana Ribeiro - 8190573
 * @author André Raro - 8190590
 *
 * Classe Manual representa a simulação automatica, esta indica qual o trajeto
 * que permita ao jogador atingir o alvo com o maior número de pontos.
 */
public class Automatic {

    /**
     * Vida inicial do Tó Cruz
     */
    private static Double LIFE_DEFAULT = 100.0;
    /**
     * Variável do tipo mission utilizada para guardar informações básicas da
     * missão
     */
    private Mission mission;
    /**
     * Vida atual do Tó Cruz
     */
    private Double vidaToCruz;
    /**
     * Guarda o caminho mais curto realizado até à divisão onde se encontra o
     * alvo
     */
    private LinkedQueue<Division> pathShort;
    /**
     * Guarda o caminho mais curto realizado apartir da divisão onde se encontra
     * o alvo que é o mesmo até chegar ao alvo mas invertido
     */
    private LinkedStack<Division> pathShortInverted;

    /**
     * Construtor que cria uma simulação automática e inicializa as variáveis. O
     * parametro missão é recebido para serem guardadas as simulações geradas.
     *
     * @param mission missão
     */
    public Automatic(Mission mission) {
        this.mission = mission;
        this.vidaToCruz = LIFE_DEFAULT;
        this.pathShort = new LinkedQueue();
        this.pathShortInverted = new LinkedStack();
    }

    /**
     * Função que contém o algoritmo responsável por calcular o caminho mais
     * curto até ao alvo. Este é igual da primeira divisão até ao alvo e do alvo
     * até à primeira divisão.
     */
    public void automatic() {
        System.out.println(smsInicial());
        Iterator<Division> itExitEntry = mission.getExitEntry().iterator();
        int tamExitEntry = mission.getExitEntry().size(), countCurrent = 0;
        double[] weight = new double[tamExitEntry];
        int[] nDivision = new int[tamExitEntry];
        Division exitEntryCurrent = null;

        while (itExitEntry.hasNext()) {
            exitEntryCurrent = itExitEntry.next();
            Iterator iSPath = mission.getBuilding().iteratorShortestPath(exitEntryCurrent, mission.getTargetDivision());
            int countDiv = 0;
            while (iSPath.hasNext()) {
                iSPath.next();
                countDiv++;
            }
            weight[countCurrent] = mission.getBuilding().shortestPathWeight(exitEntryCurrent, mission.getTargetDivision()) + exitEntryCurrent.getEnemiesPower();
            nDivision[countCurrent] = countDiv;
            countCurrent++;
        }

        int nMinDivisionPOS = 0;
        int minWeight = 0;
        for (int i = 1; i < tamExitEntry; i++) {
            if (nDivision[i] < nDivision[nMinDivisionPOS]) {
                nMinDivisionPOS = i;
            }
            if (weight[i] < weight[minWeight]) {
                minWeight = i;
            }
        }

        int nShorthestPath = nMinDivisionPOS;
        if (nMinDivisionPOS != minWeight) {
            nShorthestPath = minWeight;
        }

        itExitEntry = mission.getExitEntry().iterator();
        int count = 0;
        this.vidaToCruz = this.vidaToCruz - weight[nShorthestPath] * 2;

        while (itExitEntry.hasNext()) {
            exitEntryCurrent = itExitEntry.next();
            if (count == nShorthestPath) {
                break;
            }
            count++;
        }
        Object currentPath = null;
        Iterator iSPath = mission.getBuilding().iteratorShortestPath(exitEntryCurrent, mission.getTargetDivision());
        while (iSPath.hasNext()) {
            currentPath = iSPath.next();
            pathShort.enqueue(currentPath);
            pathShortInverted.push((Division) currentPath);
        }

        String s = "";
        s += "Finalizou o modo automatico com " + this.vidaToCruz + "  pontos de vida";
        s += "\n  Perdeu " + weight[nShorthestPath] + " pontos a chegar alvo e mais " + weight[nShorthestPath] + " a sair do edificio";

        System.out.println(smsFinal(s));
    }

    /**
     * String s que contém a informação relativa ao caminho percorrido
     *
     * @param sms String com a informação relativa ao caminho
     * @return sms
     */
    private String smsFinal(String sms) {
        String s = "";
        LinkedQueue pathShortTemp = pathShort;
        LinkedStack pathShortInvertedTemp = pathShortInverted;
        Division current = null;

        s += "-Caminho até ao Alvo: \n";
        while (!pathShortTemp.isEmpty()) {
            current = (Division) pathShortTemp.dequeue();
            s += "\t" + current.getName() + "\n";
        }
        s += "-Caminho do Alvo até a saida: \n";
        while (!pathShortInvertedTemp.isEmpty()) {
            current = (Division) pathShortInvertedTemp.pop();
            s += "\t" + current.getName() + "\n";
        }

        s += "\n" + sms;

        if (this.vidaToCruz < this.LIFE_DEFAULT) {
            s += "\n \n -*-*-INFELIZMENTE A MISSÃO FOI CONCLUIDA MAS SEM SUCESSO -*-*- \n";
        } else {
            s += "\n \n-*-*- MISSÃO CONCLUIDA COM SUCESSO -*-*- \n";
        }
        return s;
    }

    /**
     * String s que contém a informação relativa à missão
     *
     * @return String s com a informação
     */
    private String smsInicial() {
        String s = "*-*-*-*-*-*-*--*-*-*-*--*-*-*-*--*-*-*--*-*-*--*-*-*-*--*-*-*-*-*-*-*\n";
        s += "\t  Simulação : Automática \n";
        s += "\t  Missão: \n";
        s += "\t\tCodigo-Missão:" + mission.getCod() + "\n";
        s += "\t\tVersão:" + mission.getVersion() + "\n";
        s += "\t\tAlvo:\n";
        s += "\t\t\tDivisão:" + mission.getTarget().getDivision() + "\n";
        s += "\t\t\tTipo:" + mission.getTarget().getType() + "\n";
        s += "*-*-*-*-*-*-*--*-*-*-*--*-*-*-*--*-*-*--*-*-*--*-*-*-*-*-*-*-*--*-*-*\n";

        return s;
    }
}
