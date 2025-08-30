#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QMessageBox>
#include <QPixmap>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    QPixmap pix{":/resource/img/studentlearning.jpg"};

    int w = ui->label_pic->width();
    int h = ui->label_pic->height();
    ui->label_pic->setPixmap(pix.scaled(w,h,Qt::KeepAspectRatio));
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_login_clicked()
{
    QString username = ui->userName->text();
    QString password = ui->password->text();
    if (username == "test" && password == "test"){
        //hide(); // hide the previous window
        QMessageBox::information(this,"Login Message","User name password is correct");
         secondWindowDialog *secondDialogue = new secondWindowDialog(this);
         secondDialogue->show();
    }
    else if (username == "" || password == ""){
        //hide(); // hide the previous window
        ui->statusBar->showMessage("Please Enter Both Usernae and password");
    }
    else {
        QMessageBox::warning(this,"Login Message","User name password is not correct");
    }
}

void MainWindow::on_actionNew_2_triggered()
{
    QMessageBox::warning(this,"New Window","You want new");
}

void MainWindow::on_actionLoad_triggered()
{
    QMessageBox::warning(this,"Load Window","You want Load");
}

void MainWindow::on_actionExit_triggered()
{
    QApplication::quit();
}
