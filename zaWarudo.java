// Environment code for project tracking.mas2j

import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;
import jason.environment.grid.Location;

import java.util.logging.Logger;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class zaWarudo extends Environment {

    public static final int GSize = 25;
    public static final int OBS = 16;

    public static final Term ml = Literal.parseLiteral("move(left)");
    public static final Term mr = Literal.parseLiteral("move(right)");
    public static final Term mu = Literal.parseLiteral("move(up)");
    public static final Term md = Literal.parseLiteral("move(down)");
    public static final Term ul = Literal.parseLiteral("update(location)");
    public static final Term gd = Literal.parseLiteral("give(direction)");

    private Logger logger = Logger.getLogger("tracking.mas2j."+zaWarudo.class.getName());

    private WorldModel model;
    private WorldView view;

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        model = new WorldModel();
        view = new WorldView(model);
        model.setView(view);
        // updatePercepts();
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        logger.info("executing: "+ action);
        if (true) { // you may improve this condition
             informAgsEnvironmentChanged();
        }
        return true; // the action was executed with success
    }

    class WorldModel extends GridWorldModel{
        Random random = new Random(System.currentTimeMillis());
        public int obsNum;
        //World Initialization
        private WorldModel(){
            super(GSize, GSize, 7);

            try{
                for(int i = 1; i < 7; i++){
                    setAgPos(i, random.nextInt(GSize), random.nextInt(GSize));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            obsNum = random.nextInt(10);
            for(int j = 0; j < obsNum; j++){
                add(OBS, random.nextInt(25), random.nextInt(25));
            }
        }
    }

    class WorldView extends GridWorldView{
        public WorldView(WorldModel model){
            super(model, "Za Warudo", 600);
            defaultFont = new Font("Arial", Font.BOLD, 18);
            setVisible(true);
            repaint();
        }

        @Override
        public void draw(Graphics g, int x, int y, int object){
            switch(object){
                case zaWarudo.OBS:
                    drawObs(g, x, y);
                    break;
            }
        }

        @Override
        public void drawAgent(Graphics g, int x, int y, Color c, int id){
            String label = "M" + (id + 1);
            c = Color.blue;

            super.drawAgent(g, x, y, c, -1);
            if (id == 0) {
                g.setColor(Color.black);
            } else {
                g.setColor(Color.white);
            }
            super.drawString(g, x, y, defaultFont, label);
            repaint();
        }

        public void drawObs(Graphics g, int x, int y){
            super.drawObstacle(g, x, y);
            g.setColor(Color.white);
            drawString(g, x, y, defaultFont, "W");
        }
    }

    /** Called before the end of MAS execution */
    // @Override
    // public void stop() {
    //     super.stop();
    // }
}

