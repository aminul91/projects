#ifndef SECONDWINDOWDIALOG_H
#define SECONDWINDOWDIALOG_H

#include <QDialog>
#include "thirdwindow.h"
namespace Ui {
class secondWindowDialog;
}

class secondWindowDialog : public QDialog
{
    Q_OBJECT

public:
    explicit secondWindowDialog(QWidget *parent = 0);
    ~secondWindowDialog();

private slots:
    void on_pushButton_clicked();

    void on_pushButton_2_clicked();

    void on_pushButton_3_clicked();

    void on_pushButton_4_clicked();

    void on_fileWindow_clicked();

private:
    Ui::secondWindowDialog *ui;
    thirdWindow *thirdWindowDialogue;
};

#endif // SECONDWINDOWDIALOG_H
