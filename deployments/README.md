
# Запуск сервера через docker-compose

### Установка docker

__UBUNTU__

1. Сначала обновите существующий список пакетов

```
sudo apt update
```

2. Затем установите несколько обязательных пакетов, которые позволяют apt использовать пакеты по HTTPS

```
sudo apt install apt-transport-https ca-certificates curl software-properties-common
```

3. Добавляем ключ GPG официального репозитория Docker в вашу систему

```
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```

4. Добавляем репозиторий Docker

```
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
```

5. Обновляем список пакетов

```
sudo apt update
```

6. Устанавливаем docker

```    
sudo apt install docker-ce
```


__По желанию можете дать root права пользотелю чтобы не вводить всегда sudo__

Добавляем своего пользователя в группу docker:

    sudo usermod -aG docker <username>


### Установка docker-compose

Запускаем эту команду для установки последней версии docker-compose:

    sudo curl -L "https://github.com/docker/compose/releases/download/1.28.6/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

Делаем файл исполняемым

    sudo chmod +x /usr/local/bin/docker-compose


### Проверка и запуск

Запуск демона:

    sudo systemctl start docker

Проверка статуса:

    sudo systemctl status docker

Проверка docker-compose, должен вывести версию

    docker-compose --version



### Запуск проекта

    cd deployments && docker-compose up


Сервер запуститься на `9099` порту

Тестовый запрос `[GET] http://127.0.0.1:9099/api/testing/hello `
