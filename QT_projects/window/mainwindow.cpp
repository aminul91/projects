#include "mainwindow.h"
#include "ui_mainwindow.h"


MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_pushButton_clicked()
{
    // windowSecondDialog secDialogue;
    // secDialogue.setModal(true);
    // secDialogue.exec();
    windowSecondDialog *secDialogue = new windowSecondDialog(this);
    secDialogue -> show();
}
