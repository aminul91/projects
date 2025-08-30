#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "secondwindowdialog.h"
namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private slots:
    void on_login_clicked();

    void on_actionNew_2_triggered();

    void on_actionLoad_triggered();

    void on_actionExit_triggered();

private:
    Ui::MainWindow *ui;
    secondWindowDialog *secondDialogue;
};

#endif // MAINWINDOW_H
